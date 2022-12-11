package com.example.homework.mapper;

import com.example.homework.dto.AccountResponse;
import com.example.homework.persistence.model.Account;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AccountMapper {

    public AccountResponse toResponse(Account account){
        return AccountResponse.builder()
                .balance(account.getBalance())
                .iban(account.getIban())
                .currency(account.getCurrency())
                .build();
    }
}
