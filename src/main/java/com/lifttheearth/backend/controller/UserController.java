package com.lifttheearth.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lifttheearth.backend.domain.User;
import com.lifttheearth.backend.dto.UpdateUserSettingRequest;
import com.lifttheearth.backend.dto.UserResponse;
import com.lifttheearth.backend.repository.UserRepository;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(new UserResponse(
                user.getId(), user.getEmail(), user.getUniqueName(), user.getDisplayName(),
                user.getCreatedAt().toString()));
    }

    @PutMapping("/setting")
    public ResponseEntity<Void> updateUserSetting(@AuthenticationPrincipal User user,
            @RequestBody UpdateUserSettingRequest request) {
        user.setDisplayName(request.getDisplayName());
        user.setUniqueName(request.getUniqueName());
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }
}