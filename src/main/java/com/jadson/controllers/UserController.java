package com.jadson.controllers;


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



}
