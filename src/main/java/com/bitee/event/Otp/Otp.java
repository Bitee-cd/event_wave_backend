package com.bitee.event.Otp;

import com.bitee.event.User.User;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.Date;

@NamedQuery(name = "Otp.findByUserEmail",query="SELECT o from Otp o WHERE o.user.email = :email")
@NamedQuery(name="findByToken",query="SELECT o from Otp o WHERE o.token = :otp ")


@Data
@Entity
@Table(name="otp")
@DynamicInsert
@DynamicUpdate
public class Otp implements Serializable {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name="token")
    private String token;

    @Column(name="created_at")
    private Date createdAt;

    @Column(name="expires_at")
    private Date expiresAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user",nullable = false)
    private User user;

}
