package com.sang.prosangserver.repositories;

import com.sang.prosangserver.entities.account.ShellUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ShellUserRepository extends JpaRepository<ShellUser, Long> {

    Optional<ShellUser> findShellUserByKeyAndAndIsDeletedFalse(String key);
}
