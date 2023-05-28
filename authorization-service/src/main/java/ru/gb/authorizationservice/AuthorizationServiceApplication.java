package ru.gb.authorizationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import ru.gb.authorizationservice.utils.JwtTokenUtil;
//
//import java.security.NoSuchAlgorithmException;

@SpringBootApplication
public class AuthorizationServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthorizationServiceApplication.class, args);
//        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
//        try {
//            jwtTokenUtil.generateKeyPair();
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
    }

}
