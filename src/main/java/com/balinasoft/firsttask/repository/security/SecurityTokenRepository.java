package com.balinasoft.firsttask.repository.security;

import com.balinasoft.firsttask.domain.security.SecurityToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface SecurityTokenRepository extends JpaRepository<SecurityToken, Long> {
    @Modifying
    @Query("delete SecurityToken s where s.securityUser.id = :userId")
    void deleteByUserId(@Param("userId") long userId);

    @Modifying
    @Query("delete SecurityToken s where s.token = :token")
    void deleteToken(@Param("token") String token);

    SecurityToken findByToken(String token);
}
