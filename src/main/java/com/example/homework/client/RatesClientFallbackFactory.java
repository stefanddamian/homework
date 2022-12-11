package com.example.homework.client;

import com.example.homework.exception.AccountsServiceException;
import com.example.homework.exception.ErrorType;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RatesClientFallbackFactory implements FallbackFactory<RatesClient> {
    @Override
    public RatesClient create(Throwable throwable) {
        return (keyHeader, base) -> {
            throw new AccountsServiceException(ErrorType.RATE_CLIENT_ERROR,
                    String.format("Could not get rates from rates server: %s", throwable.getMessage()), throwable);
        };
    }
}
