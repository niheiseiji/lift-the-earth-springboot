package com.lifttheearth.backend.controller;

import com.lifttheearth.backend.domain.User;
import com.lifttheearth.backend.dto.LoginRequest;
import com.lifttheearth.backend.dto.SignupRequest;
import com.lifttheearth.backend.repository.UserRepository;
import com.lifttheearth.backend.security.JwtService;
import com.lifttheearth.backend.service.UserService;
import com.lifttheearth.backend.util.JwtCookieUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest req, HttpServletResponse response) {
        User user = userService.register(req.getEmail(), req.getPassword());
        String token = jwtService.generateToken(user);
        JwtCookieUtil.addJwtToResponse(response, token);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req, HttpServletResponse response) {
        User user = userService.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(user);
        JwtCookieUtil.addJwtToResponse(response, token);

        return ResponseEntity.ok().build(); // トークンは返さず Cookie に保存
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        JwtCookieUtil.clearJwt(response);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("refresh_token".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }

        if (refreshToken == null || !jwtService.isRefreshTokenValid(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String email = jwtService.extractEmail(refreshToken);
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null || !refreshToken.equals(user.getRefreshToken())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String newJwt = jwtService.generateToken(user);
        JwtCookieUtil.addJwtToResponse(response, newJwt);
        return ResponseEntity.ok().build();
    }
}