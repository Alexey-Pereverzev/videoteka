package ru.gb.gatewayservice.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
//import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;

@Component
public class JwtUtil {
//    @Value("${jwt.secret}")
    private final byte[] secret;
//    private String secret;

    public JwtUtil() throws IOException {
        this.secret = getPublicKey();
    }

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
        return (!validateToken(token).equals(""));
    }


//    public boolean isInvalid(String token) {
//        return this.isTokenExpired(token);
//    }

    public String validateToken(final String token) {
        if (this.getAllClaimsFromToken(token).getExpiration().before(new Date())) {
            return "Token is expired";
        } else {
            try {
                Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
                return "";
            } catch (Exception e) {
                return "Token is not valid";
            }
        }
    }

    private byte[] getPublicKey() throws IOException {
        File publicKeyFile = new File("public.key");
        return Files.readAllBytes(publicKeyFile.toPath());
    }



}

