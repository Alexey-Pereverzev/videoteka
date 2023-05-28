package ru.gb.authorizationservice.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.io.*;
import java.nio.file.Files;
import java.security.*;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {
    @Value("${jwt.secret}")
//    private final byte[] secret;
    private String secret;

//    public JwtTokenUtil() throws IOException {
//        this.secret = getPrivateKey();
//    }

    @Value("${jwt.lifetime}")
    private Integer jwtLifetime;

    public String generateToken(UserDetails userDetails, Long userId) {
        Map<String, Object> claims = new HashMap<>();
        String role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList().get(0);   //  в UserDetails только 1 роль
        claims.put("role", role);                           // список ролей

        Date issuedDate = new Date();               //  время создания токена
        Date expiredDate = new Date(issuedDate.getTime() + jwtLifetime);    //  время окончания жизни токена
        return Jwts.builder()
                .setClaims(claims)                          //  роль пользователя
                .setSubject(String.valueOf(userId))         //  Id пользователя
                .setIssuedAt(issuedDate)                    //  время создания
                .setExpiration(expiredDate)                 //  время окончания жизни
//                .signWith(SignatureAlgorithm.RS512, secret) //  подпись
                .signWith(SignatureAlgorithm.HS256, secret) //  подпись
                .compact();                                 //  сборка токена
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public void generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(512);
        KeyPair pair = generator.generateKeyPair();

        byte[] privateKey = pair.getPrivate().getEncoded();
        byte[] publicKey = pair.getPublic().getEncoded();

//        StringBuffer pubKeyString = new StringBuffer();
//        for (byte b : publicKey) {
//            pubKeyString.append(Integer.toHexString(0x0100 + (b & 0x00FF)).substring(1));
//        }
//        System.out.println(pubKeyString);
//
//        StringBuffer privKeyString = new StringBuffer();
//        for (byte b : privateKey) {
//            privKeyString.append(Integer.toHexString(0x0100 + (b & 0x00FF)).substring(1));
//        }
//        System.out.println(privKeyString);

        try (FileOutputStream fos = new FileOutputStream("public.key")) {
            fos.write(publicKey);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (FileOutputStream fos = new FileOutputStream("private.key")) {
            fos.write(privateKey);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private byte[] getPrivateKey() throws IOException {
        File privateKeyFile = new File("private.key");
        return Files.readAllBytes(privateKeyFile.toPath());
    }



//    public String validateToken(final String token) {
//        try {
//            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
//            return "";
//        } catch (Exception e) {
//            return "Token is not valid";
//        }
//    }



    public String getUserIdFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public String getRole(String token) {
        return getAllClaimsFromToken(token).get("role", String.class);
    }
}
