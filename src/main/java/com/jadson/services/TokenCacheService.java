package com.jadson.services;

import com.jadson.models.entities.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class TokenCacheService {

    private final CacheManager cacheManager;
    private final AuthorizationService authorizationService; // para carregar user quando não há cache

    private Cache getUsersCache() {
        return cacheManager.getCache("users");
    }

    /**
     * Retorna User (UserDetails) do cache se existir, senão carrega via authorizationService e cacheia.
     */
    public User getUser(String username) {
        Cache cache = getUsersCache();
        if (cache != null) {
            User cached = cache.get(username, User.class);
            if (cached != null) {
                log.debug("CACHE HIT for user '{}'", username);
                return cached;
            } else {
                log.debug("CACHE MISS for user '{}'", username);
            }
        } else {
            log.warn("Cache 'users' não encontrada");
        }

        // carrega via service (pode lançar UsernameNotFoundException se não existir)
        User user = (User) authorizationService.loadUserByUsername(username);
        if (cache != null && user != null) {
            cache.put(username, user);
            log.debug("User '{}' put into cache", username);
        }
        return user;
    }

    public Optional<User> getCachedUserIfPresent(String username) {
        Cache cache = getUsersCache();
        if (cache == null) return Optional.empty();
        User cached = cache.get(username, User.class);
        if (cached != null) {
            log.debug("getCachedUserIfPresent: HIT '{}'", username);
            return Optional.of(cached);
        } else {
            log.debug("getCachedUserIfPresent: MISS '{}'", username);
            return Optional.empty();
        }
    }


    public void evictUser(String username) {
        Cache cache = getUsersCache();
        if (cache != null) {
            cache.evict(username);
            log.warn("Evicted cache entry for '{}'", username);
        }
    }

    public void putUser(User user) {
        Cache cache = getUsersCache();
        if (cache != null && user != null) {
            cache.put(user.getUsername(), user);
            log.warn("Put user '{}' into cache (manual)", user.getUsername());
        }
    }
}
