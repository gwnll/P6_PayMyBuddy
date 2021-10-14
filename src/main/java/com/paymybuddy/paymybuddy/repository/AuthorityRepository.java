package com.paymybuddy.paymybuddy.repository;


import com.paymybuddy.paymybuddy.model.Authorities;
import com.paymybuddy.paymybuddy.model.Authority;
import org.springframework.data.repository.CrudRepository;

public interface AuthorityRepository extends CrudRepository<Authority, Integer> {

    Authority findByCode(Authorities authorities);

}
