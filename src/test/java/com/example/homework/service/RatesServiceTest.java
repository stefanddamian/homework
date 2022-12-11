package com.example.homework.service;

import com.example.homework.cache.Cache;
import com.example.homework.client.RatesClient;
import com.example.homework.dto.RatesClientResponse;
import com.example.homework.exception.AccountsServiceException;
import com.example.homework.exception.ErrorType;
import com.example.homework.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletionException;

@ExtendWith(MockitoExtension.class)
public class RatesServiceTest {
    @InjectMocks
    private RatesService ratesService;
    @Mock
    private RatesClient ratesClient;
    @Mock
    private Cache<String, Map<String, Double>> ratesCache;

    @Test
    void shouldGetRateFromCache(){
        Mockito.when(ratesCache.get("RON")).thenReturn(Optional.of(Map.of("EUR", 0.2)));

        Double response = ratesService.getRate("RON", "EUR");

        Assertions.assertEquals(0.2, response);
    }

    @Test
    void shouldGetNewRates(){
        RatesClientResponse ratesClientResponse = TestUtils.buildRatesClientResponse("RON");
        Mockito.when(ratesCache.get("RON")).thenReturn(Optional.empty());
        Mockito.when(ratesClient.getRates(Mockito.any(), Mockito.eq("RON")))
                .thenReturn(Optional.of(ratesClientResponse));

        Double response = ratesService.getRate("RON", "EUR");

        Mockito.verify(ratesCache, Mockito.times(1)).put("RON", ratesClientResponse.getRates());

        Assertions.assertEquals(0.2, response);
    }

    @Test
    void shouldThrowWhenGetNewRates_rateNotFound(){
        RatesClientResponse ratesClientResponse = TestUtils.buildRatesClientResponse("RON");
        Mockito.when(ratesCache.get("RON")).thenReturn(Optional.empty());
        Mockito.when(ratesClient.getRates(Mockito.any(), Mockito.eq("RON"))).thenReturn(
                Optional.of(ratesClientResponse));

        AccountsServiceException exception = Assertions.assertThrows(AccountsServiceException.class, () -> ratesService.getRate("RON", "IRN"));

        Mockito.verify(ratesCache, Mockito.times(1)).put("RON", ratesClientResponse.getRates());

        Assertions.assertNotNull(exception);
        Assertions.assertSame(ErrorType.RATE_NOT_FOUND, exception.getErrorType());
    }

    @Test
    void shouldThrowWhenGetNewRates_rateClientError(){
        RatesClientResponse ratesClientResponse = TestUtils.buildRatesClientResponse("RON");
        Mockito.when(ratesCache.get("RON")).thenReturn(Optional.empty());
        Mockito.when(ratesClient.getRates(Mockito.any(), Mockito.eq("RON")))
                .thenThrow(new AccountsServiceException(ErrorType.RATE_CLIENT_ERROR, "test"));

        AccountsServiceException exception = Assertions.assertThrows(AccountsServiceException.class, () -> ratesService.getRate("RON", "IRN"));

        Mockito.verify(ratesCache, Mockito.times(0)).put("RON", ratesClientResponse.getRates());

        Assertions.assertNotNull(exception);
        Assertions.assertSame(ErrorType.RATE_CLIENT_ERROR, exception.getErrorType());
    }
}
