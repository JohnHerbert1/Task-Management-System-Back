package com.jadson.config;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        // cria caches "users" para UserDetails / entidade e "tokenVersions" (opcional)
        return new ConcurrentMapCacheManager("users", "tokenVersions");
    }
}
