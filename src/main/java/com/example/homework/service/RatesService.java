package com.example.homework.service;

import com.example.homework.cache.Cache;
import com.example.homework.client.RatesClient;
import com.example.homework.dto.RatesClientResponse;
import com.example.homework.exception.AccountsServiceException;
import com.example.homework.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class RatesService {
    private final Cache<String, Map<String, Double>> ratesCache;
    private final RatesClient ratesClient;
    @Value("${api.rates.key}")
    private String apiKey;

    public Double getRate(String fromCurrency, String toCurrency) {
        return ratesCache.get(fromCurrency)
                .map(rates -> rates.get(toCurrency))
                .orElseGet(() -> getNewRatesForCurrency(fromCurrency, toCurrency));
    }

    private RatesClientResponse updateRates(RatesClientResponse newRates){
        ratesCache.put(newRates.getBase(), newRates.getRates());
        return newRates;
    }

    private Double getNewRatesForCurrency(String fromCurrency, String toCurrency){
        log.info("Update rates for {} currency", fromCurrency);
        return ratesClient.getRates(apiKey, fromCurrency)
                .map(this::updateRates)
                .map(RatesClientResponse::getRates)
                .map(rates -> rates.get(toCurrency))
                .orElseThrow(() ->{
                    throw new AccountsServiceException(ErrorType.RATE_NOT_FOUND,
                            String.format("Rate not found between %s and %s", fromCurrency, toCurrency));
                });
    }
}
