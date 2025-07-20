package com.locus_narrative.auth_service.presentation.controllers;

import com.locus_narrative.auth_service.application.dto.IResponse;
import com.locus_narrative.auth_service.application.dto.Responses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    /*
    POST: sign-in body: login, password
    return jwt

    POST: localhost:8080/auth/sign-in
     */


    @GetMapping("/sign-in")
    private ResponseEntity<IResponse<?>> signIn(@RequestParam String login, @RequestParam String password) {
        return ResponseEntity.ok(Responses.ok(login + " " + password));
    }
}
