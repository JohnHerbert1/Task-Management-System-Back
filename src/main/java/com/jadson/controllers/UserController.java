package com.jadson.controllers;

import com.jadson.config.JwtTokenProvider;
import com.jadson.dto.requests.LoginRequest;
import com.jadson.dto.requests.TokenDTO;
import com.jadson.dto.requests.UserDTO;
import com.jadson.models.entities.User;
import com.jadson.services.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl service;
    private final JwtTokenProvider jwtTokenProvider;

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
            String token = jwtTokenProvider.generateToken(user.getUsername(), user.getTokenVersion());
            TokenDTO tokenDTO = new TokenDTO(token, user.getEmail());
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Token inválido");
        }

        String token = header.substring(7);

        if (!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.status(401).body("Token inválido ou expirado");
        }

        // Invalida o token atual incrementando o tokenVersion
        String email = jwtTokenProvider.getUsername(token);
        service.incrementTokenVersion(email); // ← Invalida todos os tokens emitidos até agora

        // Limpa o contexto de segurança
        SecurityContextHolder.clearContext();

        return ResponseEntity.ok("Logout realizado com sucesso");
    }
}
