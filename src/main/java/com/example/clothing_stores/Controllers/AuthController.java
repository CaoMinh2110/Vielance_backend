package com.example.clothing_stores.Controllers;

import com.example.clothing_stores.Config.CookieUtils;
import com.example.clothing_stores.Dto.requests.UserLoginRequest;
import com.example.clothing_stores.Exception.AppException;
import com.example.clothing_stores.Enums.ErrorCode;
import com.example.clothing_stores.Services.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {
    AuthService service;
    CookieUtils cookie;

    @PostMapping("/login")
    ResponseEntity<?> login (@RequestBody @Valid UserLoginRequest request) {
        return ResponseEntity.ok()
                .header("Set-Cookie", service.authenticate(request).toString())
                .body(Map.of("ok", true));
    }

    @PostMapping("/logout")
    ResponseEntity<?> logout(HttpServletRequest request) {
        if (request.getCookies() != null)
            for (Cookie c : request.getCookies())
                if (c.getName().equals(cookie.getCookieName()))
                    return ResponseEntity.ok()
                            .header("Set-Cookie", service.logout(c.getValue()).toString())
                            .body(Map.of("ok", true));
        throw new AppException(ErrorCode.UNAUTHENTICATED);
    }

    @PostMapping("/refresh-token")
    ResponseEntity<?> refresh(HttpServletRequest request) {
        if (request.getCookies() != null)
            for (Cookie c : request.getCookies())
                if (c.getName().equals(cookie.getCookieName()))
                    return ResponseEntity.ok()
                            .header("Set-Cookie", service.refreshToken(c.getValue()).toString())
                            .body(Map.of("ok", true));
        throw new AppException(ErrorCode.UNAUTHENTICATED);
    }
}
