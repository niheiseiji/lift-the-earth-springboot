package com.lifttheearth.backend.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "display_name", length = 255)
    private String displayName;

    @Column(name = "unique_name", nullable = false, unique = true)
    private String uniqueName;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "refresh_token")
    private String refreshToken;
}