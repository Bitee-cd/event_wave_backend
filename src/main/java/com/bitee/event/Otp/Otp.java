package com.bitee.event.Otp;

import com.bitee.event.User.User;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.Date;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user",nullable = false)
    private User user;

}
