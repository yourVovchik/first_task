package com.balinasoft.firsttask.repository.security;

import com.balinasoft.firsttask.domain.security.SecurityRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface SecurityRoleRepository extends JpaRepository<SecurityRole, Long> {
}
