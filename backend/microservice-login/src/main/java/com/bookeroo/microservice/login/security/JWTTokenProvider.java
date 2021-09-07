package com.bookeroo.microservice.login.security;

import com.bookeroo.microservice.login.model.User;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTTokenProvider {

    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        String userId = Long.toString(user.getId());

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", (Long.toString(user.getId())));
        claims.put("username", user.getUsername());
        claims.put("firstName", user.getFirstName());
        claims.put("lastName", user.getLastName());

        Date now = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(now.getTime() + SecurityConstant.EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(userId)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SecurityConstant.SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SecurityConstant.SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (SignatureException exception) {
            System.out.println("Invalid JWT signature");
        } catch (MalformedJwtException exception) {
            System.out.println("Invalid JWT token");
        } catch (ExpiredJwtException exception) {
            System.out.println("Expired JWT token");
        } catch (UnsupportedJwtException exception) {
            System.out.println("Unsupported JWT token");
        } catch (IllegalArgumentException exception) {
            System.out.println("JWT claims is empty");
        }

        return false;
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(SecurityConstant.SECRET_KEY).parseClaimsJws(token).getBody();
        String id = (String) claims.get("id");

        return Long.parseLong(id);
    }

}
