package com.example.homework.integration;

import com.example.homework.dto.AccountCreationRequest;
import com.example.homework.dto.AccountResponse;
import com.example.homework.service.AccountsService;
import com.example.homework.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
@AutoConfigureWireMock(port = 0)
public class AccountsServiceTest {
    @Autowired
    private AccountsService accountsService;

    @Test
    void testEntireFlow(){
        Long id = accountsService.createAccount(TestUtils.buildAccountCreationRequest("RON"));

        String ratesResponse = """
                    {
                        "base": "RON",
                        "rates": {
                            "EUR": 0.2
                        }
                    }
                """;

        RatesServiceSteps.expectRatesClientResponse("RON", ratesResponse);

        AccountResponse response = accountsService.getAccount(id, Optional.of("EUR"));

        Assertions.assertEquals("EUR", response.getCurrency());
        Assertions.assertEquals(2000L, response.getBalance());
        Assertions.assertNotNull(response.getIban());
    }
}
