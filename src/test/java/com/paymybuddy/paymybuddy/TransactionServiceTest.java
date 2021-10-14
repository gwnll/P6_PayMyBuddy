package com.paymybuddy.paymybuddy;

import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.repository.UserRepository;
import com.paymybuddy.paymybuddy.service.TransactionService;
import com.paymybuddy.paymybuddy.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TransactionServiceTest {

    @Autowired
    TransactionService transactionService;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Test
    public void addTransactionTest() {
        // GIVEN
        User sender = new User();
        sender.setSold(130);
        sender.setEmail("sender@mail.fr");
        User receiver = new User();
        receiver.setSold(170);
        receiver.setEmail("receiver@mail.fr");
        userRepository.save(sender);
        userRepository.save(receiver);

        // WHEN
        transactionService.addTransaction("sender@mail.fr", "receiver@mail.fr", 30, "envoi de 30 euros");

        // THEN
        User senderResult = userService.getUser("sender@mail.fr").get();
        User receiverResult = userService.getUser("receiver@mail.fr").get();
        Assertions.assertThat(senderResult.getSold()).isEqualTo(99.85);
        Assertions.assertThat(receiverResult.getSold()).isEqualTo(200);
    }


}
