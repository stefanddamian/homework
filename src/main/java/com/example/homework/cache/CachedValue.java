package com.example.homework.cache;

public record CachedValue<V>(V value, Long timestamp) {
}
