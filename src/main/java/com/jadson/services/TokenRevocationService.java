package com.jadson.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class TokenRevocationService {

    private final CacheManager cacheManager;

    private Cache getRevokedCache() {
        return cacheManager.getCache("revokedTokens");
    }

    /**
     * Revoga token pela jti; expiryEpochMillis = token expiration time in ms
     */
    public void revokeToken(String jti, long expiryEpochMillis) {
        Cache cache = getRevokedCache();
        if (cache != null && jti != null) {
            cache.put(jti, expiryEpochMillis);
            log.warn("Token revogado: {}, expira em {}", jti, expiryEpochMillis);
        }
    }

    /**
     * Verifica se token com jti foi revogado. Remove entradas expiradas encontradas.
     */
    public boolean isRevoked(String jti) {
        if (jti == null) return false;
        Cache cache = getRevokedCache();
        if (cache == null) return false;
        Long expiry = cache.get(jti, Long.class);
        if (expiry == null) return false;
        if (System.currentTimeMillis() > expiry) {
            // expirou — limpar entrada para evitar crescimento indefinido
            cache.evict(jti);
            log.warn("Entrada revogada expirada removida: {}", jti);
            return false;
        }
        return true;
    }

    /**
     * Opcional: evict manual (se quiser)
     */
    public void evict(String jti) {
        Cache cache = getRevokedCache();
        if (cache != null) cache.evict(jti);
    }
}
