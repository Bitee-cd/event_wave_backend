package com.bitee.event.Otp;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRepository extends JpaRepository<Otp,Long> {
    Otp findByUserEmail(String email);

    Otp findByToken(String otp);

}
