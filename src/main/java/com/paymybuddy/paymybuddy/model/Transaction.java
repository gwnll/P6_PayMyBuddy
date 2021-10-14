package com.paymybuddy.paymybuddy.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name="transaction")
public class Transaction implements ITransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="amount", nullable = false)
    private double amount;

    @Column(name="sign", nullable = false)
    private Sign sign;

    @Column(name="date", nullable = false)
    private LocalDateTime date;

    @OneToOne
    private User user;

    @Override
    public boolean isInternal() {
        return false;
    }
}
