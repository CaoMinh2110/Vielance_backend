package com.example.clothing_stores.Services;

import com.example.clothing_stores.Config.CookieUtils;
import com.example.clothing_stores.Dto.requests.UserLoginRequest;
import com.example.clothing_stores.Exception.AppException;
import com.example.clothing_stores.Enums.ErrorCode;
import com.example.clothing_stores.Models.InvalidatedToken;
import com.example.clothing_stores.Models.User.AppUser;
import com.example.clothing_stores.Repo.InvalidateTokenRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {
    InvalidateTokenRepository repository;

    AuthenticationManager authManager;
    JwtService jwt;
    CookieUtils cookie;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long expiration;

    public ResponseCookie authenticate (UserLoginRequest request){
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.account(), request.password()));
        SecurityContextHolder.getContext().setAuthentication(auth);

        AppUser user = (AppUser) auth.getPrincipal();

        String token = jwt.generateToken(user);

        return cookie.buildCookie(token, expiration);
    }

    public ResponseCookie logout (String token)   {
        try {
            var signedJWT = jwt.vetifyToken(token, false);
            String jti = signedJWT.getJWTClaimsSet().getJWTID();
            Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

            repository.save(new InvalidatedToken(jti, expiryTime));

            return cookie.buildCookie("", 0);
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }

    public ResponseCookie refreshToken(String token) {
        try {
            var newToken = jwt.refreshToken(token);

            return cookie.buildCookie(newToken, expiration);
        } catch (Exception e){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }
}
