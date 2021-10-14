package com.paymybuddy.paymybuddy.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="internalTransaction")
public class InternalTransaction implements ITransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="amount", nullable = false)
    private double amount;

    @Column(name="date", nullable = false)
    private LocalDateTime date;

    @Column(name="description")
    private String description;

    @OneToOne
    private User sender;

    @OneToOne
    private User receiver;

    @Override
    public boolean isInternal() {
        return true;
    }
}
