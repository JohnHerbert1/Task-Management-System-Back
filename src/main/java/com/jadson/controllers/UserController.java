package com.jadson.controllers;

import com.jadson.config.JwtTokenProvider;
import com.jadson.dto.requests.LoginRequest;
import com.jadson.dto.requests.TokenDTO;
import com.jadson.dto.requests.UserDTO;
import com.jadson.models.entities.User;
import com.jadson.services.TokenCacheService;
import com.jadson.services.TokenRevocationService;
import com.jadson.services.UserServiceImpl;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl service;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenCacheService tokenCacheService;
    private final TokenRevocationService tokenRevocationService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid UserDTO dto) {
        service.creat(dto);
        return ResponseEntity.status(201).body("Sucesso ao cadastrar");
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody @Valid LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.email(),
                            loginRequest.password()
                    )
            );
            User user = (User) authentication.getPrincipal();

            var roles = user.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            String token = jwtTokenProvider.generateToken(user.getUsername(), user.getTokenVersion(),roles);
            TokenDTO tokenDTO = new TokenDTO(token, user.getEmail());
            tokenCacheService.putUser(user);
            return ResponseEntity.ok(tokenDTO);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/lista")
    public List<UserDTO> allUser() {
        return service.getAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("update")

    public ResponseEntity<String> update(
            @RequestBody @Valid UserDTO dto) {
         service.updateUser(dto);

        return ResponseEntity.status(201).body("Sucesso ao atualizar usuario");
    }

    /* -----------------------------
     LOGOUT
     - Espera header Authorization: Bearer <token>
     - Valida token, extrai claims (jti, exp, sub)
     - Se jti presente: revoga jti (logout individual)
     - Se jti ausente: incrementa tokenVersion (logout global)
     - Evict do cache do usuário e limpa SecurityContext
   ------------------------------*/

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String header) {
        // Verifica existência e formato do header
        if (header == null || !header.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Token inválido");
        }

        // Remove "Bearer "
        String token = header.substring(7);

        // Valida token estrutural/assinatura/expiração
        if (!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.status(401).body("Token inválido ou expirado");
        }

        // Extrai claims do token
        Claims claims = jwtTokenProvider.parseClaims(token);
        String jti = claims.getId(); // identificador único do token
        String email = jwtTokenProvider.getUsername(token); // subject (username/email)
        Date exp = claims.getExpiration(); // data de expiração

        // Se não tem jti -> fallback para logout global (incrementa tokenVersion)
        if (jti == null) {
            log.warn("Logout chamado em token sem JTI — realizando logout global para {}", email);
            service.incrementTokenVersion(email); // invalida todos tokens antigos
            tokenCacheService.evictUser(email);   // limpa cache do usuário
            SecurityContextHolder.clearContext(); // remove autenticação atual
            return ResponseEntity.ok("Logout global realizado (token sem jti)");
        }

        // Calcula millis de expiração para manter entrada de revogado até o token expirar
        long expiryMillis = exp != null ? exp.getTime() : (System.currentTimeMillis() + 600_000L); // fallback 10 min
        tokenRevocationService.revokeToken(jti, expiryMillis); // registra jti como revogado

        // Evict do cache do usuário (opcional mas recomendado)
        tokenCacheService.evictUser(email);
        SecurityContextHolder.clearContext();
        log.warn("Token jti={} revogado para usuário {}", jti, email);
        return ResponseEntity.ok("Logout realizado (token revogado)");
    }
}
