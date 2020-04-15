package com.marcinrosol.carrental.services;

import com.marcinrosol.carrental.exceptions.user.UserAlreadyExistException;
import com.marcinrosol.carrental.exceptions.user.UserNotFoundException;
import com.marcinrosol.carrental.models.Enums.RoleName;
import com.marcinrosol.carrental.models.Role;
import com.marcinrosol.carrental.models.User;
import com.marcinrosol.carrental.repositories.RoleRepository;
import com.marcinrosol.carrental.repositories.UserRepository;
import com.marcinrosol.carrental.security.jwt.JwtTokenProvider;
import com.marcinrosol.carrental.security.payload.ApiResponse;
import com.marcinrosol.carrental.security.payload.JwtAuthenticationResponse;
import com.marcinrosol.carrental.security.payload.Requests.LoginRequest;
import com.marcinrosol.carrental.security.payload.Requests.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthService {

    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager,
                       JwtTokenProvider jwtTokenProvider,
                       UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public JwtAuthenticationResponse login(LoginRequest loginRequest) {
        System.out.println("LOGIN SERVICE");
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = jwtTokenProvider.generateToken(auth);
        JwtAuthenticationResponse response = new JwtAuthenticationResponse();
        response.setTokenType(jwt);
        return response;
    }


    public ApiResponse register(RegisterRequest registerRequest) {
        System.out.println("REGISTER SERVICE");
        if(userRepository.existsByUsername(registerRequest.getUsername())){
            throw new UserAlreadyExistException("User with username: '"+registerRequest.getUsername()+"' already exist!");
        }
        if(userRepository.existsByEmail(registerRequest.getEmail())){
            throw new UserAlreadyExistException("User with email: '"+registerRequest.getEmail()+"' already exist!");
        }
        User user = new User();
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setUsername(registerRequest.getUsername());

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> {
                    throw new UserNotFoundException("Role not found!"); //TODO: CREATE EXCEPTION
                });
        user.setRoles(Collections.singleton(userRole));
        User result = userRepository.save(user);
        return new ApiResponse(true, "User registered!");
    }
}
