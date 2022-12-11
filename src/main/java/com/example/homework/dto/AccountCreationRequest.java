package com.example.homework.dto;

import lombok.Value;

@Value
public class AccountCreationRequest {
    String currency;
    Long initialBalance;
}
