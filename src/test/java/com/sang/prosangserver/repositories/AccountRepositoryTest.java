package com.sang.prosangserver.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.sang.prosangserver.entities.Account;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AccountRepositoryTest {
	
	@Autowired 
	private DataSource dataSource;
	
	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Test
	void injectedComponentsAreNotNull(){
		assertThat(dataSource).isNotNull();
		assertThat(entityManager).isNotNull();
		assertThat(accountRepository).isNotNull();
	}
	
}
