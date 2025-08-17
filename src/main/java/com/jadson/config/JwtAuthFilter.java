package com.jadson.config;

import com.jadson.models.entities.User;
import com.jadson.services.AuthorizationService;
import com.jadson.services.TokenCacheService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthorizationService authorizationService;
    private final TokenCacheService tokenCacheService;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (jwtTokenProvider.validateToken(token)) {
                String username = jwtTokenProvider.getUsername(token);
                Claims claims = jwtTokenProvider.parseClaims(token);
                var userDetails = authorizationService.loadUserByUsername(username);
                Integer verInToken = claims.get("ver", Integer.class);
                Integer currentVer = ((User)userDetails).getTokenVersion();


                User user = tokenCacheService.getCachedUserIfPresent(username)
                        .orElseGet(() -> tokenCacheService.getUser(username));

                try {
                    Optional<User> maybeUser = tokenCacheService.getCachedUserIfPresent(username);
                    user = maybeUser.orElseGet(() -> tokenCacheService.getUser(username)); // getUser carrega via AuthorizationService se necessário
                } catch (org.springframework.security.core.userdetails.UsernameNotFoundException e) {
                    // subject do token não existe mais (ex.: email antigo) -> ignora autenticação
                    filterChain.doFilter(request, response);
                    return;
                } catch (RuntimeException e) {
                    filterChain.doFilter(request, response);
                    return;
                }

                //  Verifica se o token foi invalidado (ex: logout)
                if (!verInToken.equals(currentVer)) {
                    filterChain.doFilter(request, response); // Token antigo → ignora autenticação
                    return;
                }

                // Pega roles do token  e monta authorities
                @SuppressWarnings("unchecked")
                List<String> rolesFromToken = (List<String>) claims.get("roles");
                var authorities = rolesFromToken == null ? user.getAuthorities()
                        : rolesFromToken.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

                var authentication = new UsernamePasswordAuthenticationToken(
                        user, null, authorities);

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
