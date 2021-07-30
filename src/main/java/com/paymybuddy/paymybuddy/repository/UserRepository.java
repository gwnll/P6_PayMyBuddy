package com.paymybuddy.paymybuddy.repository;

import com.paymybuddy.paymybuddy.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    @Query(value = "SELECT u FROM User u " +
            "LEFT JOIN u.authorities " +
            "LEFT JOIN u.contacts " +
            "WHERE u.email = :email ")
    Optional<User> findByEmail(String email);

}
