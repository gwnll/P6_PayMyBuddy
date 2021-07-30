package com.paymybuddy.paymybuddy.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="bank_account")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="iban", unique = true)
    private String iban;

    @Column(name="sold")
    private int sold;

    @OneToOne
    private User owner;

    @OneToMany
    private List<Transaction> transactions;

}
