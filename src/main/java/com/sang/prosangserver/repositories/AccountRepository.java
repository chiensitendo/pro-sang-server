package com.sang.prosangserver.repositories;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sang.prosangserver.entities.account.Account;

@Repository
@Transactional
public interface AccountRepository extends JpaRepository<Account, Long> {
	public Optional<Account> getOneByIdAndIsDeletedIsFalse(Long id);
	public Optional<Account> getOneByEmailOrUsernameAndIsDeletedIsFalse(String email, String username);
	public Optional<Account> getOneByUsernameAndIsDeletedIsFalse(String username);
	public void deleteByUsername(String username);

	public boolean existsAccountByIdAndIsDeletedIsFalse(Long id);
}
