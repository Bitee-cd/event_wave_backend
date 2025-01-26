package com.bitee.event.OtpTests;

import com.bitee.event.Otp.Otp;
import com.bitee.event.Otp.OtpRepository;
import com.bitee.event.User.AccountStatus;
import com.bitee.event.User.User;
import com.bitee.event.User.UserRepository;
import com.bitee.event.User.UserRole;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.util.SystemPropertyUtils;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class OtpRepositoryTest {
    static final String TOKEN = "000000";
    @Autowired
    UserRepository userRepository;
    @Autowired
    OtpRepository otpRepository;



    String email = "Biteedev33@mailinator.com";
    String phoneNumber = "+2347062314249";
    String firstName = "Caleb";
    String lastName = "Bitiyong";
    String password = "Bitiyongdev45?";
    User createUser() {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhoneNumber(phoneNumber);
        user.setStatus(AccountStatus.ACTIVE);
        user.setRole(UserRole.USER);
        return user;
    }
    Otp createOtp(){
        User user = createUser() ;
        Otp otp = new Otp();
        otp.setToken(TOKEN);
        otp.setCreatedAt(new Date());
        otp.setUser(user);
        otp.setExpiresAt(new Date(System.currentTimeMillis()+15*60*1000));
        return otp;
    }

    @Test
    @Transactional
    @Rollback
    void createAndSaveOtp(){
        User user = createUser();
        User savedUser = userRepository.save(user);

        Otp otp = createOtp();
        Otp savedOtp = otpRepository.save(otp);

        assertNotNull(savedOtp);
        assertNotNull(savedOtp.getId());
        assertEquals(savedUser,savedOtp.getUser());
        assertEquals(TOKEN,savedOtp.getToken());

    }
    @Test
    @Transactional
    @Rollback
    void findUserByEmailTest(){
        User user = createUser();
        User savedUser = userRepository.save(user);

        Otp otp = createOtp();
        Otp savedOtp = otpRepository.save(otp);

       Otp foundOtp = otpRepository.findByUserEmail(user.getEmail());

       assertNotNull(foundOtp);
    }

    @Test
    @Transactional
    @Rollback
    void findUserByTokenTest(){
        User user = createUser();
        User savedUser = userRepository.save(user);

        Otp otp = createOtp();
        Otp savedOtp = otpRepository.save(otp);

        Otp foundOtp = otpRepository.findByToken(TOKEN);

        assertNotNull(foundOtp);
    }
}
