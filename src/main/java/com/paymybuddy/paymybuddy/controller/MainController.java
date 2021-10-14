package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MainController {

    private UserService userService;

    public MainController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
    public ModelAndView index(Model model) {

        String message = "Hello Spring Boot + JSP";

        model.addAttribute("message", message);

        return new ModelAndView("index");
    }

    @RequestMapping(value = { "/usersList" }, method = RequestMethod.GET)
    public String viewUsersList(Model model) {

        List<User> users = (List<User>) userService.getUsers();

        model.addAttribute("users", users);

        return "usersList";
    }

    @RequestMapping("/contactsList")
    public String getContacts()
    {
        return "Welcome Admin";
    }



    @Autowired
    private PasswordEncoder passwordEncoder;
}
