package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.model.InternalTransaction;
import com.paymybuddy.paymybuddy.model.Sign;
import com.paymybuddy.paymybuddy.model.Transaction;
import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.repository.InternalTransactionRepository;
import com.paymybuddy.paymybuddy.repository.TransactionRepository;
import com.paymybuddy.paymybuddy.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TransactionService {

    private final InternalTransactionRepository internalTransactionRepository;

    private final TransactionRepository transactionRepository;

    private final UserRepository userRepository;

    public TransactionService(InternalTransactionRepository internalTransactionRepository, TransactionRepository transactionRepository, UserRepository userRepository) {
        this.internalTransactionRepository = internalTransactionRepository;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;

    }

    @Transactional
    public InternalTransaction addTransaction(String senderEmail, String receiverEmail, double amount, String description) {
        User sender = userRepository.findByEmail(senderEmail).orElseThrow(() -> new UsernameNotFoundException(senderEmail));
        User receiver = userRepository.findByEmail(receiverEmail).orElseThrow(() -> new UsernameNotFoundException(receiverEmail));
        double tax = amount * 0.005;
        InternalTransaction internalTransaction = new InternalTransaction();
        internalTransaction.setAmount(amount);
        internalTransaction.setDescription(description);
        internalTransaction.setSender(sender);
        internalTransaction.setReceiver(receiver);
        internalTransaction.setDate(LocalDateTime.now());
        internalTransactionRepository.save(internalTransaction);
        sender.setSold((sender.getSold() - amount - tax));
        receiver.setSold((receiver.getSold() + amount));
        userRepository.save(sender);
        userRepository.save(receiver);
        return internalTransaction;
    }

    @Transactional
    public Transaction editSold(User user, String type, double amount) {
        double newSold = 0;
        Transaction transaction = new Transaction();
        if (type.equals("débit")) {
            newSold = user.getSold() - amount;
            transaction.setSign(Sign.MINUS);
        }
        if (type.equals("crédit")) {
            newSold = user.getSold() + amount;
            transaction.setSign(Sign.PLUS);
        }
        transaction.setAmount(amount);
        transaction.setDate(LocalDateTime.now());
        transaction.setUser(user);
        transactionRepository.save(transaction);
        user.setSold(newSold);
        userRepository.save(user);
        return transaction;
    }

}
