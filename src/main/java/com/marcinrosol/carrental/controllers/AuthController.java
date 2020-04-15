package com.marcinrosol.carrental.controllers;

import com.marcinrosol.carrental.security.payload.ApiResponse;
import com.marcinrosol.carrental.security.payload.JwtAuthenticationResponse;
import com.marcinrosol.carrental.security.payload.Requests.LoginRequest;
import com.marcinrosol.carrental.security.payload.Requests.RegisterRequest;
import com.marcinrosol.carrental.services.AuthService;
import com.marcinrosol.carrental.validation.MapValidationService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    private AuthService authService;
    private MapValidationService mapValidationService;

    @Autowired
    public AuthController(AuthService authService,
                          MapValidationService mapValidationService) {
        this.authService = authService;
        this.mapValidationService= mapValidationService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest, BindingResult result){
        ResponseEntity<?> errorMap = mapValidationService.MapValidationService(result);
        if(errorMap != null) return errorMap;

        return new ResponseEntity<JwtAuthenticationResponse>(authService.login(loginRequest), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest, BindingResult result){
        ResponseEntity<?> errorMap = mapValidationService.MapValidationService(result);
        if(errorMap != null) return errorMap;

        return new ResponseEntity<ApiResponse>(authService.register(registerRequest), HttpStatus.OK);
    }
}
