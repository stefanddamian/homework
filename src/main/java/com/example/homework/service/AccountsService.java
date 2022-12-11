package com.example.homework.service;

import com.example.homework.dto.AccountCreationRequest;
import com.example.homework.dto.AccountResponse;
import com.example.homework.exception.AccountsServiceException;
import com.example.homework.exception.ErrorType;
import com.example.homework.mapper.AccountMapper;
import com.example.homework.persistence.model.Account;
import com.example.homework.persistence.repository.AccountsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountsService {
    private final AccountsRepository repository;
    private final ExchangeService exchangeService;

    public AccountResponse getAccount(Long id, Optional<String> currency){
        AccountResponse accountData = getAccountIfExist(id);
        return currency.filter(c -> !c.equals(accountData.getCurrency()))
                .filter(this::validateCurrency)
                .map(newCurrency -> exchangeService.changeCurrency(accountData, newCurrency))
                .orElse(accountData);
    }

    public Long createAccount(AccountCreationRequest creationContext){
        return Optional.of(creationContext)
                .filter(data -> validateCurrency(data.getCurrency()))
                .map(this::buildAccount)
                .map(repository::save)
                .map(Account::getId)
                .orElseThrow(() -> new AccountsServiceException(ErrorType.CREATION_ERROR,
                        "Could not create new account"));
    }

    private AccountResponse getAccountIfExist(Long id){
        return repository.findById(id)
                .map(AccountMapper::toResponse)
                .orElseThrow(() -> new AccountsServiceException(ErrorType.ACCOUNT_NOT_FOUND,
                        String.format("Account with id %d not found.", id)));
    }

    // TODO better validation
    private boolean validateCurrency(String currency){
        return Optional.of(currency)
                .map(c -> c.length() == 3)
                .filter(Boolean::booleanValue)
                .orElseThrow(() -> new AccountsServiceException(ErrorType.INVALID_CURRENCY,
                        String.format("%s is not a valid currency", currency)));
    }

    private Account buildAccount(AccountCreationRequest creationContext){
        return Account.builder()
                .currency(creationContext.getCurrency())
                .balance(creationContext.getInitialBalance())
                .iban(UUID.randomUUID().toString())
                .build();
    }
}
