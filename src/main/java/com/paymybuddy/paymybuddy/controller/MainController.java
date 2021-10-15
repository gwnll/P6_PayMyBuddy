package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.dto.*;
import com.paymybuddy.paymybuddy.model.ITransaction;
import com.paymybuddy.paymybuddy.model.InternalTransaction;
import com.paymybuddy.paymybuddy.model.Transaction;
import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.service.TransactionService;
import com.paymybuddy.paymybuddy.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MainController {

    final private UserService userService;
    final private TransactionService transactionService;

    public MainController(UserService userService, TransactionService transactionService) {
        this.userService = userService;
        this.transactionService = transactionService;
    }

    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
    public ModelAndView index(Model model, @AuthenticationPrincipal User user) {

        double sold = userService.getUser(user.getEmail()).map(User::getSold).orElseThrow();

        model.addAttribute("sold", sold);

        return new ModelAndView("index");
    }

    @RequestMapping(value = { "/contacts" }, method = RequestMethod.GET)
    public ModelAndView viewContactsList(Model model, @AuthenticationPrincipal User user) {

        List<User> contacts = userService.getContacts(user.getEmail());

        model.addAttribute("contacts", contacts);

        return new ModelAndView("contacts");

    }

    @RequestMapping(value = { "/addContact" }, method = RequestMethod.POST)
    public ModelAndView addContact(Model model, @AuthenticationPrincipal User user, AddContactDTO request) {

        try {
            List<User> contacts = userService.addContact(user.getEmail(), request.getContactName());
            model.addAttribute("contacts", contacts);
        }
        catch (RuntimeException e) {
            model.addAttribute("contacts", userService.getContacts(user.getEmail()));
            model.addAttribute("error", e.getMessage());
        }

        return new ModelAndView("contacts");
    }

    @RequestMapping(value = { "/bankAccount" }, method = RequestMethod.GET)
    public ModelAndView viewBankAccount(Model model, @AuthenticationPrincipal User user) {

        String bankAccount = user.getIban();

        model.addAttribute("bankAccount", bankAccount);

        return new ModelAndView("bankAccount");
    }

    @RequestMapping(value = { "/editBankAccount" }, method = RequestMethod.POST)
    public ModelAndView editBankAccount(Model model, @AuthenticationPrincipal User user, EditBankAccountDTO request) {

        String bankAccount = userService.addBankAccount(user, request);

        model.addAttribute("bankAccount", bankAccount);

        return new ModelAndView("bankAccount");
    }

    @RequestMapping(value = { "/transactions" }, method = RequestMethod.GET)
    public ModelAndView viewTransactions(Model model, @AuthenticationPrincipal User user) {

        List<ITransaction> transactions = userService.getTransactions(user);

        List<User> contacts = userService.getContacts(user.getEmail());

        double sold = userService.getUser(user.getEmail()).map(User::getSold).orElseThrow();

        String currentUser = user.getEmail();

        model.addAttribute("sold", sold);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("transactions", transactions);
        model.addAttribute("contacts", contacts);

        return new ModelAndView("transactions");
    }

    @RequestMapping(value = { "/editSold" }, method = RequestMethod.POST)
    public ModelAndView editSold(Model model, @AuthenticationPrincipal User user,  EditSoldDTO request) {

        try {
            Transaction transaction = transactionService.editSold(user, request.getType(), request.getAmount());
            model.addAttribute("message", "La transaction a bien été effectuée !");
        }
        catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
        }

        List<ITransaction> transactions = userService.getTransactions(user);
        List<User> contacts = userService.getContacts(user.getEmail());
        double sold = userService.getUser(user.getEmail()).map(User::getSold).orElseThrow();

        String currentUser = user.getEmail();

        model.addAttribute("sold", sold);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("transactions", transactions);
        model.addAttribute("contacts", contacts);

        return new ModelAndView("transactions");
    }


    @RequestMapping(value = { "/sendMoney" }, method = RequestMethod.POST)
    public ModelAndView addTransaction(Model model, @AuthenticationPrincipal User user,  AddTransactionDTO request) {

        try {
            InternalTransaction transaction = transactionService.addTransaction(user.getEmail(), request.getContactName(), request.getAmount(), request.getDescription());
            model.addAttribute("message", "La transaction a bien été effectuée !");
        }
        catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
        }

        List<ITransaction> transactions = userService.getTransactions(user);
        List<User> contacts = userService.getContacts(user.getEmail());
        double sold = userService.getUser(user.getEmail()).map(User::getSold).orElseThrow();

        String currentUser = user.getEmail();

        model.addAttribute("sold", sold);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("transactions", transactions);
        model.addAttribute("contacts", contacts);

        return new ModelAndView("transactions");
    }

    @RequestMapping(value = { "/subscription" }, method = RequestMethod.GET)
    public ModelAndView subscription (Model model) {
        return new ModelAndView("subscription");
    }

    @RequestMapping(value = { "/subscribe" }, method = RequestMethod.POST)
    public ModelAndView addUser(Model model, AddUserDTO request) {

        try {
            userService.registerNewUserAccount(request.getEmail(), request.getPassword());
        }
        catch(RuntimeException e) {
            model.addAttribute("error", e.getMessage());

            return new ModelAndView("subscription");
        }

        model.addAttribute("created", true);

        return new ModelAndView("login");
    }

}
