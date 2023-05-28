package ru.gb.authorizationservice.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {
//    @Value("${jwt.secret}")
    private final byte[] secret;
//    private String secret;

    public JwtTokenUtil() throws IOException {
        this.secret = getPrivateKey();
    }

    @Value("${jwt.lifetime}")
    private Integer jwtLifetime;

    private Algorithm buildJwtAlgorithm(byte[] publicKeyBytes, byte[] privateKeyBytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory kf = KeyFactory.getInstance("RSA");
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        PublicKey publicKey = kf.generatePublic(publicKeySpec);
        RSAPrivateKey privateKey = (RSAPrivateKey) kf.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));

        return Algorithm.RSA512((RSAPublicKey) publicKey, privateKey);
    }

    public String generateToken(UserDetails userDetails, Long userId) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        Map<String, Object> claims = new HashMap<>();
        String role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList().get(0);   //  в UserDetails только 1 роль
        claims.put("role", role);                           // список ролей

        Date issuedDate = new Date();               //  время создания токена
        Date expiredDate = new Date(issuedDate.getTime() + jwtLifetime);    //  время окончания жизни токена
//        return Jwts.builder()
//                .setClaims(claims)                          //  роль пользователя
//                .setSubject(String.valueOf(userId))         //  Id пользователя
//                .setIssuedAt(issuedDate)                    //  время создания
//                .setExpiration(expiredDate)                 //  время окончания жизни
//                .signWith(buildJwtAlgorithm(getPublicKey(), getPrivateKey()), secret) //  подпись
////                .signWith(SignatureAlgorithm.HS256, secret) //  подпись
//                .compact();                                 //  сборка токена
        return JWT.create()
                .withClaim("role", role)
                .withSubject(String.valueOf(userId))
                .withIssuedAt(issuedDate)
                .withExpiresAt(expiredDate)
                .sign(buildJwtAlgorithm(getPublicKey(), getPrivateKey()));

    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public void generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(1024);
        KeyPair pair = generator.generateKeyPair();
        PrivateKey privateKey = pair.getPrivate();
        PublicKey publicKey = pair.getPublic();

//        byte[] privateKey = pair.getPrivate().getEncoded();
//        byte[] publicKey = pair.getPublic().getEncoded();

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
            fos.write(publicKey.getEncoded());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        try (FileOutputStream fos = new FileOutputStream("public.key")) {
//            fos.write(publicKey);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        try (FileOutputStream fos = new FileOutputStream("private.key")) {
            fos.write(privateKey.getEncoded());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private byte[] getPrivateKey() throws IOException
//             NoSuchAlgorithmException, InvalidKeySpecException
    {
        File privateKeyFile = new File("private.key");
        byte[] privateKeyBytes = Files.readAllBytes(privateKeyFile.toPath());
//        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//        EncodedKeySpec privateKeySpec = new X509EncodedKeySpec(privateKeyBytes);
//        return keyFactory.generatePrivate(privateKeySpec);
        return privateKeyBytes;
    }

    private byte[] getPublicKey() throws IOException {
        File publicKeyFile = new File("public.key");
        return Files.readAllBytes(publicKeyFile.toPath());
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
