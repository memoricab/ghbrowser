package com.repostats.ghbackend.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
@Data
public class User implements Serializable {
    private static final long serialVersionUID = -3325678594582671708L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Email
    @Column(nullable = false)
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    @Column(nullable = false)
    private String githubAccessToken;
    private String providerId;
}
