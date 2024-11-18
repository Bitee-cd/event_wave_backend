package com.bitee.event.UserTests;

import com.bitee.event.User.AccountStatus;
import com.bitee.event.User.User;
import com.bitee.event.User.UserRepository;
import com.bitee.event.User.UserRole;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;
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

    @Test
    @Transactional
    @Rollback
    public void saveUser() {

        User user = createUser();

        User savedUser = userRepository.save(user);

        assertNotNull(savedUser);

        assertNotNull(savedUser.getId());
        assertEquals(email, savedUser.getEmail());
        assertEquals(firstName, savedUser.getFirstName());
    }

    @Test
    @Transactional
    @Rollback
    public void FindByEmailTest() {
        User user = createUser();
        userRepository.save(user);

        User foundUser = userRepository.findByUserEmail(email);

        assertNotNull(foundUser);
        assertEquals(password, foundUser.getPassword());
        assertEquals(lastName, foundUser.getLastName());

    }

    @Test
    @Rollback
    @Transactional
    public void FindByEmailNotFoundTest() {

        User notFoundUser = userRepository.findByUserEmail("nonexistent@email.com");
        assertNull(notFoundUser);


    }
}
