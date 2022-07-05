package org.example.caches;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @author Mohammad Aiub Khan
 */
public class MyCacheStore1<T> {
    private final Cache<String, T> cache;

    public MyCacheStore1(int expiryDuration, TimeUnit timeUnit) {
        cache = CacheBuilder.newBuilder()
                .expireAfterWrite(expiryDuration, timeUnit)
                .concurrencyLevel(Runtime.getRuntime().availableProcessors())
                .build();
    }

    public T get(String key) {
        return cache.getIfPresent(key);
    }

    public void add(String key, T value) {
        if (key != null && value != null) {
            cache.put(key, value);
            System.out.println("Record stored in " + value.getClass().getSimpleName() + " Cache with Key = " + key);
        }
    }
}
