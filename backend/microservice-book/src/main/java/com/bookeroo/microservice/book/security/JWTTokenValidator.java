package com.bookeroo.microservice.book.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

@Component
public class JWTTokenValidator {

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
