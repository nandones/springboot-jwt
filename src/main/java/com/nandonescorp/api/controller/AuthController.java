package com.nandonescorp.api.controller;

import com.nandonescorp.api.dto.UserRequestDTO;
import com.nandonescorp.api.dto.UserResponseDTO;
import com.nandonescorp.api.model.User;
import com.nandonescorp.api.security.JwtService;
import com.nandonescorp.api.security.UserDetailsImpl;
import com.nandonescorp.api.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserRequestDTO dto) {
        return ResponseEntity.ok(userService.register(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequestDTO dto) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.username(), dto.password())
        );
        UserDetailsImpl principal = (UserDetailsImpl) auth.getPrincipal();
        String token = jwtService.generateToken(principal, principal.getId());
        return ResponseEntity.ok().body("{\"token\":\"" + token + "\"}");
    }
}
