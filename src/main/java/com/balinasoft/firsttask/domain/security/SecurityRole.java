package com.balinasoft.firsttask.domain.security;

import com.balinasoft.firsttask.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "security_role")
public class SecurityRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ROLE role;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public SecurityRole() {
    }

    public SecurityRole(ROLE role, User user) {
        this.role = role;
        this.user = user;
    }

    public enum ROLE {
        ADMIN,
        USER
    }
}
