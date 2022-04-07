package com.sang.prosangserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sang.prosangserver.entities.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

}