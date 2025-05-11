package com.lifttheearth.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String email;
    private String uniqueName;
    private String displayName;
    private String profileImageUrl;
    private String createdAt;
}
