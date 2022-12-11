package com.example.homework.service;

import com.example.homework.dto.AccountCreationRequest;
import com.example.homework.dto.AccountResponse;
import com.example.homework.exception.AccountsServiceException;
import com.example.homework.exception.ErrorType;
import com.example.homework.persistence.model.Account;
import com.example.homework.persistence.repository.AccountsRepository;
import com.example.homework.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @InjectMocks
    private AccountsService accountsService;

    @Mock
    private AccountsRepository repository;

    @Mock
    private ExchangeService exchangeService;

    @Test
    void shouldGetAccount_noExchange(){
        Mockito.when(repository.findById(TestUtils.ACCOUNT_ID)).thenReturn(Optional.of(TestUtils.buildAccount()));

        AccountResponse response = accountsService.getAccount(TestUtils.ACCOUNT_ID, Optional.empty());

        Assertions.assertEquals(TestUtils.buildAccountResponse(), response);
    }

    @Test
    void shouldGetAccount_withExchange(){
        AccountResponse expected = AccountResponse.builder().currency("EUR").build();

        Mockito.when(repository.findById(TestUtils.ACCOUNT_ID)).thenReturn(Optional.of(TestUtils.buildAccount()));
        Mockito.when(exchangeService.changeCurrency(TestUtils.buildAccountResponse(), "EUR")).thenReturn(expected);

        AccountResponse response = accountsService.getAccount(TestUtils.ACCOUNT_ID, Optional.of("EUR"));

        Assertions.assertEquals(expected, response);
    }

    @Test
    void shouldThrowAccountNotFound(){
        Mockito.when(repository.findById(TestUtils.ACCOUNT_ID)).thenReturn(Optional.empty());

        AccountsServiceException exception = Assertions.assertThrows(AccountsServiceException.class,
                () -> accountsService.getAccount(TestUtils.ACCOUNT_ID, Optional.empty()));

        Assertions.assertNotNull(exception);
        Assertions.assertSame(ErrorType.ACCOUNT_NOT_FOUND, exception.getErrorType());
    }



    @Test
    void shouldCreateAccount(){
        AccountCreationRequest request = TestUtils.buildAccountCreationRequest(TestUtils.CURRENCY);
        Mockito.when(repository.save(Mockito.any(Account.class))).thenReturn(TestUtils.buildAccount());

        Long response = accountsService.createAccount(request);

        Assertions.assertEquals(TestUtils.ACCOUNT_ID, response);
    }

    @Test
    void shouldThrowWhenCreateAccount_invalidCurrency(){
        AccountCreationRequest request = TestUtils.buildAccountCreationRequest("ROND");

        AccountsServiceException exception = Assertions.assertThrows(AccountsServiceException.class,
                () -> accountsService.createAccount(request));

        Assertions.assertNotNull(exception);
        Assertions.assertSame(ErrorType.INVALID_CURRENCY, exception.getErrorType());
    }

    @Test
    void shouldThrowWhenCreateAccount_saveFailed(){
        AccountCreationRequest request = TestUtils.buildAccountCreationRequest("RON");
        Mockito.when(repository.save(Mockito.any(Account.class))).thenReturn(null);

        AccountsServiceException exception = Assertions.assertThrows(AccountsServiceException.class,
                () -> accountsService.createAccount(request));

        Assertions.assertNotNull(exception);
        Assertions.assertSame(ErrorType.CREATION_ERROR, exception.getErrorType());
    }
}
