package com.example.homework.client;

import com.example.homework.dto.RatesClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(name = "rates-client", url = "${api.rates.url}", fallbackFactory = RatesClientFallbackFactory.class)
public interface RatesClient {
    @GetMapping("/exchangerates_data/latest")
    Optional<RatesClientResponse> getRates(@RequestHeader("apikey") String key,
                                           @RequestParam(name = "base") String base);
}
