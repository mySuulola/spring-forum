package com.suulola.forum.security;

import com.suulola.forum.exception.ForumCustomException;
import com.suulola.forum.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Base64;

@Service
public class JwtProvider {

    private KeyStore keyStore;

    @PostConstruct
    public void init() {
        try{
            keyStore = keyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/springblog.jks");
            keyStore.load(resourceAsStream, "secret".toCharArray());

        }  catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new ForumCustomException("Exception occurred while loading keystore");
        }
    }
    public String generateToken(Authentication authentication) {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

        String encodedString = Base64.getEncoder().encodeToString("secret".getBytes());
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .signWith(getPrivateKey())
                .compact();
    }

    private Key getPrivateKey() {
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        return key;
//            return (PrivateKey) keyStore.getKey("springforum", "secret".toCharArray());
    }
}
