package com.allkp.project_management_system.controller;

import com.allkp.project_management_system.config.JwtProvider;
import com.allkp.project_management_system.model.User;
import com.allkp.project_management_system.repository.UserRepository;
import com.allkp.project_management_system.request.LoginRequest;
import com.allkp.project_management_system.response.AuthResponse;
import com.allkp.project_management_system.service.SubscriptionService;
import com.allkp.project_management_system.serviceImpl.CustomUserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserDetailsImpl customUserDetailsImpl;

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {

        User isUserExist = userRepository.findByEmail(user.getEmail());

        if (isUserExist != null) {
            throw new Exception("This email already exist with another account");
        }

        User createdUser = new User();
        createdUser.setEmail(user.getEmail());
        createdUser.setPassword(passwordEncoder.encode(user.getPassword()));
        createdUser.setFullName(user.getFullName());

        User savedUser = userRepository.save(createdUser);

        subscriptionService.createSubscription(savedUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = JwtProvider.generateToken(authentication);

        AuthResponse res = new AuthResponse();
        res.setMessage("Sign-Up Successfully......");
        res.setJwt(jwt);

        return new ResponseEntity<>(res, HttpStatus.CREATED);

    }

    @PostMapping("/signing")
    public ResponseEntity<AuthResponse> signing(@RequestBody LoginRequest loginRequest) throws Exception {

        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = JwtProvider.generateToken(authentication);

        AuthResponse res = new AuthResponse();
        res.setMessage("Sign-In Successfully......");
        res.setJwt(jwt);

        return new ResponseEntity<>(res, HttpStatus.CREATED);


    }


    private Authentication authenticate(String username, String password) {

        UserDetails userDetails = customUserDetailsImpl.loadUserByUsername(username);

        if(userDetails == null){
            throw new BadCredentialsException("Invalid username or password........");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid username or password........");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}
