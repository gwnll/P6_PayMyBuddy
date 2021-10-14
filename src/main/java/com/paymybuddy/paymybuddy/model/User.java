package com.paymybuddy.paymybuddy.model;

import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "customer")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "sold")
    private double sold;

    @Column(name = "iban")
    private String iban;

    @ManyToMany
    private List<User> contacts;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Authority> authorities;

    @OneToMany(mappedBy = "user")
    private List<Transaction> transactions;

    @OneToMany(mappedBy = "sender")
    private List<InternalTransaction> internalTransactionsAsSender;

    @OneToMany(mappedBy = "receiver")
    private List<InternalTransaction> internalTransactionsAsReceiver;

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
