package com.example.loginauthapi.controllers;

import com.example.loginauthapi.domain.user.User;
import com.example.loginauthapi.dtos.auth.LoginRequestBodyDTO;
import com.example.loginauthapi.dtos.auth.AuthResponseDTO;
import com.example.loginauthapi.dtos.auth.RegisterRequestBodyDTO;
import com.example.loginauthapi.exceptions.EmailAlreadyInUseException;
import com.example.loginauthapi.exceptions.UserNotFoundException;
import com.example.loginauthapi.exceptions.WrongPasswordException;
import com.example.loginauthapi.infra.security.TokenService;
import com.example.loginauthapi.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestBodyDTO body) {
        User user = this.userRepository.findByEmail(body.email()).orElseThrow(UserNotFoundException::new);

        if (passwordEncoder.matches(body.password(), user.getPassword())) {
            String token = this.tokenService.generateToken(user);

            return ResponseEntity.ok(new AuthResponseDTO(user.getName(), token));
        }

        throw new WrongPasswordException();
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterRequestBodyDTO body) {
        if (this.userRepository.findByEmail(body.email()).isPresent()) {
            throw new EmailAlreadyInUseException();
        }

        User newUser = new User();
        newUser.setName(body.name());
        newUser.setEmail(body.email());
        newUser.setPassword(this.passwordEncoder.encode(body.password()));

        this.userRepository.save(newUser);

        String token = this.tokenService.generateToken(newUser);

        return ResponseEntity.ok(new AuthResponseDTO(newUser.getName(), token));
    }
}
