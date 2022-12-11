package com.example.homework.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AccountResponse {
    Long balance;
    String currency;
    String iban;
}
