package com.example.homework.mapper;

import com.example.homework.dto.AccountResponse;
import com.example.homework.persistence.model.Account;
import com.example.homework.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AccountMapperTest {

    @Test
    void testAccountToResponse(){
        Account account = TestUtils.buildAccount();

        AccountResponse response = AccountMapper.toResponse(account);

        Assertions.assertEquals(TestUtils.buildAccountResponse(), response);
    }
}
