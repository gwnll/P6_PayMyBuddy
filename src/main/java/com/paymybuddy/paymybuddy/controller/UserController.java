package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Read - Get all users
     *
     * @return - An Iterable object of Users full filled
     */
    @GetMapping("/users")
    public Iterable<User> getUsers() {
        Iterable<User> result = userService.getUsers();
        return result;
    }
}
