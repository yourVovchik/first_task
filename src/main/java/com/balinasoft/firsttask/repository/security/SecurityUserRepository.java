package com.balinasoft.firsttask.repository.security;

import com.balinasoft.firsttask.domain.security.SecurityUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface SecurityUserRepository extends JpaRepository<SecurityUser, Long> {
    @Query("select u from SecurityUser u where u.user.login = :login")
    SecurityUser findByLogin(@Param("login") String login);

}
