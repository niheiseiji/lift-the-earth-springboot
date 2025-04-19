package com.lifttheearth.backend.service;

import com.lifttheearth.backend.domain.User;
import com.lifttheearth.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User register(String email, String rawPassword) {
        String hashed = passwordEncoder.encode(rawPassword);
        User user = new User();
        user.setEmail(email);
        user.setPassword(hashed);
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
