package com.example.authdemo.controllers;

import com.example.authdemo.models.AuthenticationRequest;
import com.example.authdemo.models.AuthenticationResponse;
import com.example.authdemo.models.FileDescriptionResponse;
import com.example.authdemo.models.ResponseMessage;
import com.example.authdemo.repository.entities.*;
import com.example.authdemo.services.*;
import com.example.authdemo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UIController {

    private final AuthenticationManager authenticationManager;
    private final MyUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final SourceCSVService sourceCSVService;
    private final TargetCSVService targetCSVService;
    private final SourceJsonService sourceJsonService;
    private final TargetJsonService targetJsonService;
    private final DescriptionService descriptionService;

    @Autowired
    public UIController(AuthenticationManager authenticationManager, MyUserDetailsService userDetailsService,
                        JwtUtil jwtUtil, SourceCSVService sourceCSVService, TargetCSVService targetCSVService,
                        SourceJsonService sourceJsonService, TargetJsonService targetJsonService,
                        DescriptionService descriptionService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.sourceCSVService = sourceCSVService;
        this.targetCSVService = targetCSVService;
        this.sourceJsonService = sourceJsonService;
        this.targetJsonService = targetJsonService;
        this.descriptionService = descriptionService;
    }

    @PostMapping("csv-source/upload/{username}")
    public ResponseEntity<ResponseMessage> uploadCSVSourceFile(@RequestParam("file") MultipartFile file, @PathVariable String username) {
        if (username == null) {
            throw new RuntimeException("Invalid username");
        }
        return getResponseEntity(file, true, username, false);
    }

    @PostMapping("json-source/upload/{username}")
    public ResponseEntity<ResponseMessage> uploadJsonSourceFile(@RequestParam("file") MultipartFile file, @PathVariable String username) {
        if (username == null) {
            throw new RuntimeException("Invalid username");
        }
        return getResponseEntity(file, true, username, true);
    }


    @PostMapping("csv-target/upload/{username}")
    public ResponseEntity<ResponseMessage> uploadCSVTargetFile(@RequestParam("file") MultipartFile file, @PathVariable String username) {
        if (username == null) {
            throw new RuntimeException("Invalid username");
        }
        return getResponseEntity(file, false, username, false);
    }

    @PostMapping("json-target/upload/{username}")
    public ResponseEntity<ResponseMessage> uploadJsonTargetFile(@RequestParam("file") MultipartFile file, @PathVariable String username) {
        if (username == null) {
            throw new RuntimeException("Invalid username");
        }
        return getResponseEntity(file, false, username, true);
    }

    @RequestMapping(value = "/source/{username}", method = RequestMethod.POST)
    public SourceDescriptionEntity postSourceDescription(@RequestBody SourceDescriptionEntity sourceDescriptionEntity, @PathVariable String username) {
        if (username == null) {
            throw new RuntimeException("Invalid username");
        }
        return this.descriptionService.storeSourceDescription(username, sourceDescriptionEntity);
    }

    @RequestMapping(value = "/target/{username}", method = RequestMethod.POST)
    public TargetDescriptionEntity postTargetDescription(@RequestBody TargetDescriptionEntity targetDescriptionEntity, @PathVariable String username) {
        if (username == null) {
            throw new RuntimeException("Invalid username");
        }
        return this.descriptionService.storeTargetDescription(username, targetDescriptionEntity);
    }

    @GetMapping("source-desc/{username}")
    public ResponseEntity<FileDescriptionResponse> getSourceInfo(@PathVariable String username) {
        if (username == null) {
            throw new RuntimeException("Invalid username");
        }
        return ResponseEntity.ok(this.descriptionService.getFileDescriptionResponse(username));
    }


    @GetMapping("matching/{username}")
    public ResponseEntity<List<SourceFileEntity>> getMatching(@PathVariable String username) {
        if (username == null) {
            throw new RuntimeException("Invalid username");
        }
        return ResponseEntity.ok(this.sourceCSVService.getSourceFileEntities(username));
    }

    @GetMapping("mismatch/{username}")
    public ResponseEntity<List<FileEntity>> getMismatch(@PathVariable String username) {
        if (username == null) {
            throw new RuntimeException("Invalid username");
        }
        return ResponseEntity.ok(this.targetCSVService.getFileEntities(username));
    }

    @GetMapping("missing/{username}")
    public ResponseEntity<List<FileEntity>> getMissing(@PathVariable String username) {
        if (username == null) {
            throw new RuntimeException("Invalid username");
        }
        List<TargetFileEntity> targetFileEntities = new ArrayList<>(this.targetCSVService.getAll(username));
        List<FileEntity> resultant = new ArrayList<>();
        targetFileEntities.forEach(fileEntity -> {
            if (fileEntity.getSourceFileEntity() == null) {
                resultant.add(fileEntity);
            }
        });
        return ResponseEntity.ok(resultant);
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
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt, authenticationRequest.getUsername()));
    }

    private ResponseEntity<ResponseMessage> getResponseEntity(MultipartFile file, boolean isSource, String username, boolean isJson) {
        String message;
        try {
            if (isJson) {
                if (isSource) {
                    sourceJsonService.save(username, file);
                } else {
                    targetJsonService.save(username, file);
                }
            } else {
                if (isSource) {
                    sourceCSVService.save(username, file);
                } else {
                    targetCSVService.save(username, file);
                }
            }
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            e.printStackTrace();
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }
}
