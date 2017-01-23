package com.balinasoft.firsttask.domain.security;

import com.balinasoft.firsttask.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "security_user")
public class SecurityUser {
    @Id
    private Integer userId;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private User user;

    private String password;

    @Column(insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date regDate;

    @OneToMany(mappedBy = "securityUser", fetch = FetchType.LAZY)
    @OrderBy("lastAccess desc")
    private List<SecurityToken> securityTokens;
}
