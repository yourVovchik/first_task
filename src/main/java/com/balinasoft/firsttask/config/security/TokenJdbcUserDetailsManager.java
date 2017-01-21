package com.balinasoft.firsttask.config.security;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class TokenJdbcUserDetailsManager extends JdbcUserDetailsManager {
    private final String USERS_BY_TOKEN = "SELECT security_users.user_id, TRUE AS enabled FROM security_tokens LEFT JOIN security_users ON security_tokens.user_id = security_users.user_id WHERE token = ?";
    private final String AUTHORITIES_BY_USER_ID = "SELECT role FROM security_roles WHERE user_id = ?";
    private final String UPDATE_LAST_ACCESS_DATE = "UPDATE security_tokens SET last_access = NOW() WHERE token = ?";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        SqlRowSet sqlRowSet = getJdbcTemplate().queryForRowSet(USERS_BY_TOKEN, username);
        if (!sqlRowSet.next()) {
            throw new UsernameNotFoundException(
                    this.messages.getMessage("JdbcDaoImpl.notFound",
                            new Object[]{username}, "Username {0} not found"));
        }

        String userId = sqlRowSet.getString("user_id");
        boolean enabled = sqlRowSet.getBoolean("enabled");

        UserDetails user = new User(userId, username, enabled, true, true, true,
                AuthorityUtils.NO_AUTHORITIES);

        Set<GrantedAuthority> dbAuthsSet = new HashSet<>();

        List<GrantedAuthority> grantedAuthorities = getJdbcTemplate().query(AUTHORITIES_BY_USER_ID,
                new String[]{userId},
                (rs, rowNum) -> {
                    String roleName = getRolePrefix() + rs.getString(1);
                    return new SimpleGrantedAuthority(roleName);
                });

        dbAuthsSet.addAll(grantedAuthorities);

        if (getEnableGroups()) {
            dbAuthsSet.addAll(loadGroupAuthorities(userId));
        }

        List<GrantedAuthority> dbAuths = new ArrayList<>(dbAuthsSet);

        if (dbAuths.size() == 0) {
            throw new UsernameNotFoundException(this.messages.getMessage(
                    "JdbcDaoImpl.noAuthority", new Object[]{username},
                    "User {0} has no GrantedAuthority"));
        }

        getJdbcTemplate().update(UPDATE_LAST_ACCESS_DATE, username);

        return createUserDetails(username, user, dbAuths);
    }
}
