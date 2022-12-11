package com.example.homework.service;

import com.example.homework.dto.AccountResponse;
import com.example.homework.persistence.model.Account;
import com.example.homework.service.ExchangeService;
import com.example.homework.service.RatesService;
import com.example.homework.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ExchangeServiceTest {
    @InjectMocks
    private ExchangeService test;
    @Mock
    private RatesService ratesService;

    @Test
    void testChangeCurrency(){
        AccountResponse input = TestUtils.buildAccountResponse();
        Mockito.when(ratesService.getRate("RON", "EUR")).thenReturn(0.2);

        AccountResponse response = test.changeCurrency(input, "EUR");

        Assertions.assertEquals("EUR", response.getCurrency());
        Assertions.assertEquals(input.getIban(), response.getIban());
        Assertions.assertEquals(2000L, response.getBalance());
    }
}
