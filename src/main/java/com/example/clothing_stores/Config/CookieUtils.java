package com.example.clothing_stores.Config;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CookieUtils {
    String name;
    String sameSite;
    boolean secure;

    public CookieUtils(
            @Value("${app.cookie.name}") String name,
            @Value("${app.cookie.same-site}") String sameSite,
            @Value("${app.cookie.secure}") boolean secure) {
        this.name = name;
        this.sameSite = sameSite;
        this.secure = secure;
    }

    public ResponseCookie buildCookie(String token, long expirationMillis) {
        return ResponseCookie.from(name, token)
                .httpOnly(true)          // JS không đọc được cookie này
                .secure(secure)          // chỉ gửi qua HTTPS nếu true
                .sameSite(sameSite)      // Lax / Strict / None
                .path("/")               // mọi request đều gửi cookie này
                .maxAge(expirationMillis / 1000)   // tuổi thọ cookie (giây)
                .build();
    }

    public String getCookieName() { return name; }
}

