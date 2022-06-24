package com.ossovita.rabbitmqapp.security.auth;

import com.ossovita.rabbitmqapp.core.dataAccess.TokenRepository;
import com.ossovita.rabbitmqapp.core.utilities.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/1.0")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {


    @Autowired
    AuthService authService;

    @Autowired
    TokenRepository tokenRepository;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/auth")
    AuthResponse handleUserAuthentication(@RequestBody Credentials credentials) {
        return authService.authenticateUser(credentials);
    }


    @PostMapping("/logout")
    GenericResponse handleLogout(@RequestHeader(name = "Authorization") String authorization) {
        String token = authorization.substring(7);
        authService.clearToken(token);
        return new GenericResponse("Logout success");
    }


}
