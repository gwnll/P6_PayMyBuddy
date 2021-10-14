package com.paymybuddy.paymybuddy;

import com.paymybuddy.paymybuddy.dto.AddUserDTO;
import com.paymybuddy.paymybuddy.model.Authorities;
import com.paymybuddy.paymybuddy.model.Authority;
import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.repository.AuthorityRepository;
import com.paymybuddy.paymybuddy.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    AuthorityRepository authorityRepository;

    @Test
    public void registerNewUserAccountTest() {
        // GIVEN
        authorityRepository.save(new Authority(1, "test", Authorities.USER));

        // WHEN
        userService.registerNewUserAccount("test@test.fr", "password");

        // THEN
        Optional<User> user = userService.getUser("test@test.fr");
        assertTrue(user.isPresent());
    }
}
