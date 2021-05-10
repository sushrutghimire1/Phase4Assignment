package com.example.authdemo;

import com.example.authdemo.models.AuthenticationRequest;
import com.example.authdemo.models.AuthenticationResponse;
import com.example.authdemo.models.ResponseMessage;
import com.example.authdemo.repository.entities.SourceFileEntity;
import com.example.authdemo.services.MyUserDetailsService;
import com.example.authdemo.services.SourceCSVService;
import com.example.authdemo.services.TargetCSVService;
import com.example.authdemo.util.JwtUtil;
import com.example.authdemo.util.SourceCSVHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class UIController {

    private final AuthenticationManager authenticationManager;
    private final MyUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final SourceCSVService sourceCSVService;
    private final TargetCSVService targetCSVService;

    @Autowired
    public UIController(AuthenticationManager authenticationManager, MyUserDetailsService userDetailsService,
                        JwtUtil jwtUtil, SourceCSVService sourceCSVService, TargetCSVService targetCSVService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.sourceCSVService = sourceCSVService;
        this.targetCSVService = targetCSVService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (SourceCSVHelper.hasCSVFormat(file)) {
            try {
                sourceCSVService.save(file);

                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }

        message = "Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

    @GetMapping("/sourceFile")
    public ResponseEntity<List<SourceFileEntity>> getAllTutorials() {
        try {
            List<SourceFileEntity> tutorials = sourceCSVService.getAllTutorials();

            if (tutorials.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(tutorials, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping("/hello")
    public String hello() {
        return "Hello world";
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
            throws Exception {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username and Password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
