package com.lifttheearth.backend.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lifttheearth.backend.domain.User;
import com.lifttheearth.backend.dto.UpdateUserSettingRequest;
import com.lifttheearth.backend.dto.UserResponse;
import com.lifttheearth.backend.repository.UserRepository;
import com.lifttheearth.backend.service.S3Service;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private S3Service s3Service;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(new UserResponse(
                user.getId(), user.getEmail(), user.getUniqueName(), user.getDisplayName(), user.getProfileImageUrl(),
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

    @PostMapping("/upload-profile-image")
    public ResponseEntity<String> uploadImage(@AuthenticationPrincipal User user,
            @RequestParam("file") MultipartFile file) throws IOException {
        String key = "profile/" + user.getId() + "_" + file.getOriginalFilename();
        String url = s3Service.upload(key, file.getInputStream(), file.getSize(), file.getContentType());

        user.setProfileImageUrl(url);
        userRepository.save(user);

        return ResponseEntity.ok(url);
    }
}