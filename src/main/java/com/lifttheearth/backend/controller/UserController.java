package com.lifttheearth.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lifttheearth.backend.domain.User;
import com.lifttheearth.backend.dto.UserResponse;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(new UserResponse(
                user.getId(), user.getEmail(), user.getCreatedAt().toString()
        ));
    }
}