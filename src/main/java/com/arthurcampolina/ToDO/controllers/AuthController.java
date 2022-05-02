package com.arthurcampolina.ToDO.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.arthurcampolina.ToDO.services.impl.AuthServiceImpl;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthServiceImpl service;

    @Autowired
    public AuthController(AuthServiceImpl service) {
        this.service = service;
    }

    @PostMapping(value = "/resetar-senha", consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> resetPassword(@RequestBody String email) {
        String token = service.createPasswordResetTokenForUser(email);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/validar-token/{token}")
    public ResponseEntity<Boolean> validateToken(@PathVariable String token) {
        if(service.validatePasswordResetToken(token)) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }
    }

    @PostMapping(value = "/alterar-senha/{token}", consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Boolean> switchPassword(@PathVariable String token, @RequestBody String password) {
        Boolean isSaved = service.switchPassword(token, password);
        return ResponseEntity.ok(isSaved);
    }
}
