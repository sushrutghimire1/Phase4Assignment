package com.example.authdemo.controllers;

import com.example.authdemo.models.AuthenticationRequest;
import com.example.authdemo.models.AuthenticationResponse;
import com.example.authdemo.services.AuthUserDetailsService;
import com.example.authdemo.services.ReconciliationService;
import com.example.authdemo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final AuthUserDetailsService authUserDetailsService;
    private final JwtUtil jwtUtil;
    private final ReconciliationService reconciliationService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager,
                                    AuthUserDetailsService authUserDetailsService,
                                    JwtUtil jwtUtil,
                                    ReconciliationService reconciliationService) {
        this.authenticationManager = authenticationManager;
        this.authUserDetailsService = authUserDetailsService;
        this.jwtUtil = jwtUtil;
        this.reconciliationService = reconciliationService;
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
            throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username and Password", e);
        }
        final UserDetails userDetails = authUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        this.reconciliationService.updateLoginReconciliation(authenticationRequest.getUsername());
        return ResponseEntity.ok(new AuthenticationResponse(jwt, authenticationRequest.getUsername()));
    }
}
