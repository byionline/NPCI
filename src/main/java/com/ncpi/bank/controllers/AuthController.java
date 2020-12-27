package com.ncpi.bank.controllers;

import com.ncpi.bank.models.User;
import com.ncpi.bank.mongo.MongoConnector;
import com.ncpi.bank.payload.request.LoginRequest;
import com.ncpi.bank.payload.request.SignupRequest;
import com.ncpi.bank.payload.response.JwtResponse;
import com.ncpi.bank.payload.response.MessageResponse;
import com.ncpi.bank.security.jwt.JwtUtils;
import com.ncpi.bank.security.services.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final MongoConnector mongoConnector;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserId(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getUsername(),
                userDetails.getUserId()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        User userExistByUserName = mongoConnector.findOne(signUpRequest.getUserId(),
                signUpRequest.getName(), User.class);
        if (userExistByUserName != null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: user already exist!!"));
        }

        User user = new User(signUpRequest.getUserId(),
                signUpRequest.getName(),
                encoder.encode(signUpRequest.getPassword()), 10000);

        mongoConnector.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
