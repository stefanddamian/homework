package com.example.homework.controller;

import com.example.homework.dto.AccountCreationRequest;
import com.example.homework.dto.AccountResponse;
import com.example.homework.service.AccountsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/homework/api")
@RequiredArgsConstructor
public class AccountsController {
    private final AccountsService accountsService;

    @GetMapping("/account/{accountId}")
    public AccountResponse getAccountWithBalanceConverted(@PathVariable Long accountId,
                                                          @RequestParam(required = false) Optional<String> currency){
        return accountsService.getAccount(accountId, currency);
    }

    @PostMapping("/account")
    public Long createAccount(@RequestBody AccountCreationRequest request){
        return accountsService.createAccount(request);
    }
}
