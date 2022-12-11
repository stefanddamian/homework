package com.example.homework.integration;

import com.github.tomakehurst.wiremock.matching.EqualToPattern;

import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;

public class RatesServiceSteps {
    public static void expectRatesClientResponse(String requestCurrency, String response) {
        var requestMatcher = get(urlPathMatching("/exchangerates_data/latest"))
                .withHeader("apikey", new EqualToPattern("test_key", true))
                .withQueryParam("base", new EqualToPattern(requestCurrency, false));

        stubFor(requestMatcher.willReturn(okJson(response)));
    }
}
