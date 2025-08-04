package com.jadson.controllers;


import com.jadson.services.PasswordResetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/password")
public class PasswordController {

    private final PasswordResetService resetSvc;

    public PasswordController(PasswordResetService svc) {
        this.resetSvc = svc;
    }

    @PostMapping("/forgot")
    public ResponseEntity<Void> forgot(@RequestParam String email) {
        resetSvc.forgotPassword(email);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/reset")
    public ResponseEntity<Void> reset(
            @RequestParam String token,
            @RequestParam String newPassword) {
        resetSvc.resetPassword(token, newPassword);
        return ResponseEntity.noContent().build();
    }
}