package com.archivalia.auth.controller;

import com.archivalia.auth.model.AppUser;
import com.archivalia.auth.repo.UserRepository;
import com.archivalia.auth.security.JwtService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    public AuthController(
            UserRepository users,
            PasswordEncoder encoder,
            JwtService jwt) {

        this.users = users;
        this.encoder = encoder;
        this.jwt = jwt;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {

        AppUser user = users.findByEmail(req.email())
                .orElse(null);

        if (user == null ||
                !encoder.matches(req.password(), user.getPassword())) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(java.util.Map.of(
                            "message",
                            "Invalid email or password"
                    ));
        }

        return ResponseEntity.ok(
                java.util.Map.of(
                		"token", jwt.generate(
                		        user.getEmail(),
                		        user.getRole()
                		),
                        "email", user.getEmail(),
                        "role", user.getRole().name()
                )
        );
    }

    public record LoginRequest(
            String email,
            String password
    ) {
    }
}