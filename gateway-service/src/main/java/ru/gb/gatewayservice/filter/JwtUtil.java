package ru.gb.gatewayservice.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
//import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;

@Component
public class JwtUtil {
    private final PublicKey secret;

    public JwtUtil() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        this.secret = getPublicKey();
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

//    private boolean isTokenExpired(String token) {
//        return this.getAllClaimsFromToken(token).getExpiration().before(new Date());
//    }

    public boolean isInvalid(String token) {
        return (!validateToken(token).equals(""));
    }


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

    private PublicKey getPublicKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        File publicKeyFile = new File("public.key");
        byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());
        KeyFactory kf = KeyFactory.getInstance("RSA");
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        return kf.generatePublic(publicKeySpec);
    }

}

