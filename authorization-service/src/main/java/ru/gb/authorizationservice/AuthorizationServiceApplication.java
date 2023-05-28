package ru.gb.authorizationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import ru.gb.authorizationservice.utils.JwtTokenUtil;
//
//import java.io.IOException;
//import java.security.NoSuchAlgorithmException;

@SpringBootApplication
public class AuthorizationServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthorizationServiceApplication.class, args);
//        JwtTokenUtil jwtTokenUtil = null;
//        try {
//            jwtTokenUtil = new JwtTokenUtil();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        try {
//            jwtTokenUtil.generateKeyPair();
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
    }

}
