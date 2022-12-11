package com.example.homework.service;

import com.example.homework.dto.AccountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangeService {
    private final RatesService ratesService;

    public AccountResponse changeCurrency(AccountResponse account, String currency){
        return AccountResponse.builder()
                .iban(account.getIban())
                .currency(currency)
                .balance(changeBalance(account, currency))
                .build();
    }

    private Long changeBalance(AccountResponse account, String currency){
        return Math.round(account.getBalance() * ratesService.getRate(account.getCurrency(), currency));
    }
}
