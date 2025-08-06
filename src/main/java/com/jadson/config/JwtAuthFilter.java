package com.jadson.config;

import com.jadson.models.entities.User;
import com.jadson.services.AuthorizationService;
import com.jadson.services.UserServiceImpl;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthorizationService authorizationService;


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

                // ⚠️ Verifica se o token foi invalidado (ex: logout)
                if (!verInToken.equals(currentVer)) {
                    filterChain.doFilter(request, response); // Token antigo → ignora autenticação
                    return;
                }

                var authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
