package com.example.homework.dto;

import lombok.Value;

import java.util.Map;

@Value
public class RatesClientResponse {
    String base;
    Map<String, Double> rates;
}
