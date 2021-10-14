package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.dto.EditBankAccountDTO;
import com.paymybuddy.paymybuddy.model.Authorities;
import com.paymybuddy.paymybuddy.model.ITransaction;
import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.repository.AuthorityRepository;
import com.paymybuddy.paymybuddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    private AuthorityRepository authorityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;

    }

    public User registerNewUserAccount(String email, String password) {

        if (getUser(email).isPresent()) {
            throw new RuntimeException(
                    "Il y a déjà un compte avec cette adresse " + email);
        }

        User user = new User();
        user.setEmail(email);

        user.setPassword(passwordEncoder.encode(password));

        user.setAuthorities(List.of(authorityRepository.findByCode(Authorities.USER)));

        return userRepository.save(user);
    }

    public Optional<User> getUser(String user) {
        return userRepository.findByEmail(user);
    }

    public List<User> getUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByEmail(s).orElseThrow(() -> new UsernameNotFoundException(s));
    }

    public List<User> getContacts(String email) {
        return userRepository.findContactsByEmail(email);
    }

    @Transactional
    public List<User> addContact(String userEmail, String contactEmail) {
        User currentUser = userRepository.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException(userEmail));
        User contact = userRepository.findByEmail(contactEmail).orElseThrow(() -> new UsernameNotFoundException(contactEmail));
        List<User> contacts = currentUser.getContacts();

        List<User> contactAlreadyAdded = contacts.stream()
                .filter(u -> u.getEmail().equals(contactEmail))
                .collect(Collectors.toList());

        if (contactAlreadyAdded.size() >= 1) {
            throw new RuntimeException(
                    "Vous avez déjà ajouté un contact avec cette adresse " + contactEmail);
        }

        currentUser.getContacts().add(contact);
        contact.getContacts().add(currentUser);
        userRepository.save(contact);

        return userRepository.save(currentUser).getContacts();
    }

    public String addBankAccount(User user, EditBankAccountDTO request) {
        user.setIban(request.getIban());
        userRepository.save(user);
        return user.getIban();
    }

    @Transactional
    public List<ITransaction> getTransactions(User user) {
        User userWithTransactions = userRepository.findByEmailWithTransactions(user.getEmail()).orElseThrow(() -> new UsernameNotFoundException(user.getEmail()));
        Stream<ITransaction> externalTransactions = userWithTransactions.getTransactions().stream().map(this::mapToInterface);
        Stream<ITransaction> internalTransactionsAsSender = userWithTransactions.getInternalTransactionsAsSender().stream().map(this::mapToInterface);
        Stream<ITransaction> internalTransactionsAsReceiver = userWithTransactions.getInternalTransactionsAsReceiver().stream().map(this::mapToInterface);
        Stream<ITransaction> internalTransaction = Stream.concat(internalTransactionsAsReceiver, internalTransactionsAsSender);
        Stream<ITransaction> transactions = Stream.concat(internalTransaction, externalTransactions);
        return transactions
                .sorted(Comparator.comparing(ITransaction::getDate))
                .collect(Collectors.toList());
    }

    private ITransaction mapToInterface(ITransaction transaction) {
        return transaction;
    }


}
