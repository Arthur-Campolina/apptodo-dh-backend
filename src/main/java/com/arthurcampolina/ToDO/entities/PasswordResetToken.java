package com.arthurcampolina.ToDO.entities;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_password_tokens")
public class PasswordResetToken extends AbstractEntity{

    @Column(unique = true)
    private String token;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;
    private Instant expiryDate;

    public PasswordResetToken(String token, User entity) {
        this.token = token;
        this.user = entity;
        this.expiryDate = Instant.now().plus(1, ChronoUnit.DAYS);
    }

}
