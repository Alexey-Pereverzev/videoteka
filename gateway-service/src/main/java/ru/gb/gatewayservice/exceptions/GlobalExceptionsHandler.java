package ru.gb.gatewayservice.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.gb.api.dtos.dto.AppError;

@ControllerAdvice
public class GlobalExceptionsHandler {

    @ExceptionHandler
    public ResponseEntity<AppError> handleExpiredJwtException(ExpiredJwtException e){
        return new ResponseEntity<>(new AppError("TOKEN_IS_EXPIRED",
                e.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> handleMalformedJwtException(MalformedJwtException e){
        return new ResponseEntity<>(new AppError("TOKEN_IS_MALFORMED",
                e.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> handleSignatureException(SignatureException e){
        return new ResponseEntity<>(new AppError("INVALID_SIGNATURE",
                e.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> handleUnsupportedJwtException(UnsupportedJwtException e){
        return new ResponseEntity<>(new AppError("UNSUPPORTED_JWT_TOKEN",
                e.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> handleIllegalArgumentException(IllegalArgumentException e){
        return new ResponseEntity<>(new AppError("EMPTY_JWT_CLAIMS_STRING",
                e.getMessage()), HttpStatus.FORBIDDEN);
    }



}

