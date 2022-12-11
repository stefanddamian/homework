package com.example.homework.cache;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CacheTest {
    @Mock
    private Clock clock;

    private Cache<String, String> cache;

    @BeforeEach
    void setUp(){
        cache = new CacheImp<>(clock, 10L);
    }

    @Test
    void shouldBeValid(){
        Mockito.when(clock.millis()).thenReturn(0L);
        cache.put("gigel", "costel");

        Mockito.when(clock.millis()).thenReturn(9999L);

        Assertions.assertEquals(Optional.of("costel"), cache.get("gigel"));
    }

    @Test
    void shouldNotBeValid(){
        Mockito.when(clock.millis()).thenReturn(0L);
        cache.put("gigel", "costel");

        Mockito.when(clock.millis()).thenReturn(10001L);

        Assertions.assertEquals(Optional.empty(), cache.get("gigel"));
    }


}
