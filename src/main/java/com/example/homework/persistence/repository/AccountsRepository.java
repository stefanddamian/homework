package com.example.homework.persistence.repository;

import com.example.homework.persistence.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountsRepository extends JpaRepository<Account, Long> {
    boolean existsAccountsByIban(String iban);
}
