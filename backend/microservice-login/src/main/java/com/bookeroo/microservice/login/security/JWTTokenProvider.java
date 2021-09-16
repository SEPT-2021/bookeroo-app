package com.bookeroo.microservice.login.security;

import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.bookeroo.microservice.login.security.SecurityConstant.JWT_EXPIRATION_TIME_MILLIS;
import static com.bookeroo.microservice.login.security.SecurityConstant.SECRET_KEY;

@Component
public class JWTTokenProvider {

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", userDetails.getUsername());
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_TIME_MILLIS))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            String username = extractUsername(token);
            return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
        } catch (SignatureException exception) {
            System.out.println("Invalid JWT signature");
        } catch (MalformedJwtException exception) {
            System.out.println("Invalid JWT token");
        } catch (ExpiredJwtException exception) {
            System.out.println("Expired JWT token");
        } catch (UnsupportedJwtException exception) {
            System.out.println("Unsupported JWT token");
        } catch (IllegalArgumentException exception) {
            System.out.println("Empty JWT claims");
        }

        return false;
    }

}
