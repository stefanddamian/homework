package com.example.homework.configuration;

import com.example.homework.cache.Cache;
import com.example.homework.cache.CacheImp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.util.Map;

@Configuration
public class CacheConfiguration {
    @Value("${cache.rates.timeInSeconds:3600}")
    private Long ratesCacheTimeInSeconds;

    @Bean
    public Clock clock(){
        return Clock.systemDefaultZone();
    }

    @Bean
    public Cache<String, Map<String, Double>> ratesCache(Clock clock){
        return new CacheImp<>(clock, ratesCacheTimeInSeconds);
    }

}
