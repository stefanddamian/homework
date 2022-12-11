package com.example.homework.utils;

import com.example.homework.dto.AccountCreationRequest;
import com.example.homework.dto.AccountResponse;
import com.example.homework.dto.RatesClientResponse;
import com.example.homework.persistence.model.Account;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class TestUtils {
    public Long BALANCE = 10000L;
    public String IBAN = "valid_iban";
    public String CURRENCY = "RON";
    public Long ACCOUNT_ID = 123L;

    public AccountResponse buildAccountResponse(){
        return AccountResponse.builder()
                .balance(BALANCE)
                .iban(IBAN)
                .currency(CURRENCY)
                .build();
    }

    public Account buildAccount(){
        return Account.builder()
                .balance(BALANCE)
                .iban(IBAN)
                .currency(CURRENCY)
                .id(ACCOUNT_ID)
                .build();
    }

    public RatesClientResponse buildRatesClientResponse(String currency){
        Map<String, Double> rates = Stream.of("RON", "USD", "EUR", "GBP")
                .collect(Collectors.toMap(String::toString, __ -> 0.2));
        return new RatesClientResponse(currency, rates);
    }

    public AccountCreationRequest buildAccountCreationRequest(String currency){
        return new AccountCreationRequest(currency, TestUtils.BALANCE);
    }
}
