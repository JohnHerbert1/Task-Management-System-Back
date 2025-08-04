package com.jadson.controllers;


import com.jadson.config.JwtTokenProvider;
import com.jadson.dto.requests.LoginRequest;
import com.jadson.dto.requests.UserDTO;
import com.jadson.services.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserServiceImpl service;
    private final JwtTokenProvider jwtTokenProvider;


    @PostMapping( value = "/register")
    public ResponseEntity<String> register(@RequestBody @Valid UserDTO dto){
        service.creat(dto);
        return ResponseEntity.status(201).body("Sucesso ao cadastrar");
    }

    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest loginRequest){
        boolean auth = service.login(loginRequest.email(), loginRequest.password());
        return auth
                ? ResponseEntity.ok("Login bem sucedido")
                : ResponseEntity.status(401).body("Credenciais inválidas");
    }

    @GetMapping("/lista")
    public List<UserDTO> allUser(){
        return service.getAll();
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

        return ResponseEntity.ok("Logout realizado com sucesso (token descartado no cliente)");
    }

}
