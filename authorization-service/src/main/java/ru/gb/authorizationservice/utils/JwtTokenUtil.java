package ru.gb.authorizationservice.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtil {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.lifetime}")
    private Integer jwtLifetime;

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        List<String> rolesList = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        claims.put("roles", rolesList);             //  список ролей

        Date issuedDate = new Date();               //  время создания токена
        Date expiredDate = new Date(issuedDate.getTime() + jwtLifetime);    //  время окончания жизни токена
        return Jwts.builder()
                .setClaims(claims)                          //  роли
                .setSubject(userDetails.getUsername())      //  имя пользователя
                .setIssuedAt(issuedDate)                    //  время создания
                .setExpiration(expiredDate)                 //  время окончания жизни
                .signWith(SignatureAlgorithm.HS256, secret) //  подпись
                .compact();                                 //  сборка токена
    }


    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUsernameFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public List<String> getRoles(String token) {
        return getAllClaimsFromToken(token).get("roles", List.class);
    }
}
