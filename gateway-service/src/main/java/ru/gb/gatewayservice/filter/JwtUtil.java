package ru.gb.gatewayservice.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpired(String token) {
        return this.getAllClaimsFromToken(token).getExpiration().before(new Date());
    }

    public boolean isInvalid(String token) {
        return (validateToken(token).equals(""));
    }

    public String validateToken(final String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            if (this.getAllClaimsFromToken(token).getExpiration().before(new Date())) {
                return "";
            } else {
                return "Token is expired";
            }
        } catch (Exception e) {
            return "Token is not valid";
        }
    }
}

