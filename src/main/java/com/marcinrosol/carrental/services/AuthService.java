package com.marcinrosol.carrental.services;

import com.marcinrosol.carrental.repositories.RoleRepository;
import com.marcinrosol.carrental.repositories.UserRepository;
import com.marcinrosol.carrental.security.jwt.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private UserRepository userRepository;
    private RoleRepository roleRepository;

}
