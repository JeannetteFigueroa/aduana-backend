package com.aduanas.msauth.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aduanas.msauth.model.Usuario;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private Key getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(
            Usuario usuario) {

        return Jwts.builder()

                .subject(usuario.getEmail())

                .claim("rol", usuario.getRol().getNombre())
                .claim("id", usuario.getId())
                .claim("rut", usuario.getRut())

                .issuedAt(new Date())

                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + expiration))

                .signWith(getKey())

                .compact();
    }

    public String extractUsername(String token) {

        Claims claims = Jwts.parser()
                .verifyWith((javax.crypto.SecretKey) getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    public boolean isTokenValid(
            String token,
            String username) {

        try {

            return extractUsername(token)
                    .equals(username);

        } catch (Exception ex) {

            return false;
        }
    }
}
