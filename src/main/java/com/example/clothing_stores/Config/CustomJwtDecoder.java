package com.example.clothing_stores.Config;

import java.text.ParseException;
import java.util.Objects;
import javax.crypto.spec.SecretKeySpec;

import com.example.clothing_stores.Exception.AppException;
import com.example.clothing_stores.Services.JwtService;
import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

@Component
public class CustomJwtDecoder implements JwtDecoder {
    @Value("${jwt.signerKey}")
    protected String signerKey;

    private NimbusJwtDecoder nimbusJwtDecoder = null;

    @Autowired
    JwtService jwtService;

    @Override
    public Jwt decode(String token) throws JwtException {
        try {
            var response = introspect(token);

            if (!response) throw new JwtException("Token invalid");
        } catch (JOSEException | ParseException e){
            throw new JwtException(e.getMessage());
        }
        if (Objects.isNull(nimbusJwtDecoder)) {
            SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512");
            nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(secretKeySpec)
                    .macAlgorithm(MacAlgorithm.HS512)
                    .build();
        }

        return nimbusJwtDecoder.decode(token);
    }

    private boolean introspect(String token) throws JOSEException, ParseException {
        boolean isValid = true;

        try {
            jwtService.vetifyToken(token, false);
        } catch (AppException e) {
            isValid = false;
        }

        return isValid;
    }
}