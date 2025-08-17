package com.jadson.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class CacheStartupLogger {

    private final CacheManager cacheManager;

    @PostConstruct
    public void showCaches() {
        log.info("Available caches: {}", cacheManager.getCacheNames());
    }
}