package com.lifttheearth.backend.controller;

import com.lifttheearth.backend.domain.User;
import com.lifttheearth.backend.security.JwtService;
import com.lifttheearth.backend.service.UserService;
import com.lifttheearth.backend.util.JwtCookieUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/auth/google")
public class GoogleAuthController {
    @Value("${frontend.url}")
    private String frontendUrl;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;


    @Value("${google.client-id}")
    private String clientId;

    @Value("${google.client-secret}")
    private String clientSecret;

    @Value("${google.redirect-uri}")
    private String redirectUri;

    @GetMapping("/login")
    public void googleLogin(HttpServletResponse response) throws IOException {
        String authUrl = UriComponentsBuilder
                .fromHttpUrl("https://accounts.google.com/o/oauth2/v2/auth")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("response_type", "code")
                .queryParam("scope", "openid email profile")
                .queryParam("access_type", "offline")
                .build()
                .toUriString();

        response.sendRedirect(authUrl);
    }

    @GetMapping("/callback")
    public void googleCallback(@RequestParam String code, HttpServletResponse response) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        // ① access_token 取得
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String tokenRequestBody =
                "code=" + code +
                        "&client_id=" + clientId +
                        "&client_secret=" + clientSecret +
                        "&redirect_uri=" + redirectUri +
                        "&grant_type=authorization_code";
        HttpEntity<String> request = new HttpEntity<>(tokenRequestBody, headers);

        ResponseEntity<Map> tokenResponseEntity = restTemplate.exchange(
                "https://oauth2.googleapis.com/token",
                HttpMethod.POST,
                request,
                Map.class
        );

        String accessToken = (String) tokenResponseEntity.getBody().get("access_token");

        // ② ユーザー情報取得
        HttpHeaders userInfoHeaders = new HttpHeaders();
        userInfoHeaders.setBearerAuth(accessToken);
        HttpEntity<Void> userInfoRequest = new HttpEntity<>(userInfoHeaders);
        ResponseEntity<Map> userInfoResponse = restTemplate.exchange(
                "https://www.googleapis.com/oauth2/v2/userinfo",
                HttpMethod.GET,
                userInfoRequest,
                Map.class
        );

        Map<String, Object> userInfo = userInfoResponse.getBody();
        String email = (String) userInfo.get("email");

        User user = userService.findByEmail(email)
                .orElseGet(() -> userService.register(email, ""));

        String jwt = jwtService.generateToken(user);
        JwtCookieUtil.addJwtToResponse(response, jwt);

        response.sendRedirect(frontendUrl + "/");
    }
}
