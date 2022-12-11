package com.example.homework.cache;

import lombok.RequiredArgsConstructor;

import java.time.Clock;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public class CacheImp<K, V> implements Cache<K, V>{
    private Map<K, CachedValue<V>> cachedValues = new ConcurrentHashMap<>();
    private final Clock clock;
    private final Long lifeSpamInSeconds;

    @Override
    public Optional<V> get(K key) {
        return cachedValues.entrySet().stream()
                .filter(entry -> entry.getKey().equals(key))
                .findAny()
                .map(Map.Entry::getValue)
                .filter(this::isValid)
                .map(CachedValue::value);
    }

    @Override
    public void put(K key, V value) {
        cachedValues.put(key, new CachedValue<>(value, clock.millis()));
    }

    private boolean isValid(CachedValue<V> cachedValue){
        return clock.millis() - cachedValue.timestamp() < lifeSpamInSeconds * 1000;

    }
}
