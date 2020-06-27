package com.suulola.forum.security;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
@Slf4j
public class JwtProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpirationMs;

    public String generateToken(Authentication authentication) {
        org.springframework.security.core.userdetails.User userPrincipal = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(userPrincipal.getPassword())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }


    public String getUsernameFromJWT(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }





    public boolean validateToken(String authToken) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(authToken);
            return true;
        } catch (ExpiredJwtException e) {
//            log.error("Invalid JWT signature: {} ", e.getMessage());
            e.printStackTrace();
        } catch (UnsupportedJwtException e) {
//            log.error("Invalid JWT signature: {} ", e.getMessage());
            e.printStackTrace();
        } catch (MalformedJwtException e) {
//            log.error("Invalid JWT signature: {} ", e.getMessage());
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
//            log.error("Invalid JWT signature: {} ", e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

}
