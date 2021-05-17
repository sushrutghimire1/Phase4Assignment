package com.example.authdemo.services;

import com.example.authdemo.models.ReconciliationActions;
import com.example.authdemo.repository.UserAuthenticationRepository;
import com.example.authdemo.repository.entities.AuthCredentialsEntity;
import com.example.authdemo.repository.entities.ReconciliationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private final UserAuthenticationRepository authenticationRepository;

    @Autowired
    public MyUserDetailsService(UserAuthenticationRepository authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthCredentialsEntity client = this.authenticationRepository.findClientByUsername(username);
        try {
            if (client != null) {
                return new User(client.getUsername(), client.getPassword(), new ArrayList<>());
            } else throw new Exception();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
