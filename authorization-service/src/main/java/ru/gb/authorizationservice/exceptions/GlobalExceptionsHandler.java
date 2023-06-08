package ru.gb.authorizationservice.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.gb.api.dtos.dto.AppError;

@ControllerAdvice
public class GlobalExceptionsHandler {
    @ExceptionHandler
    public ResponseEntity<AppError> handleResourceNotFoundException(ResourceNotFoundException e){
        return new ResponseEntity<>(new AppError("RESOURCE_NOT_FOUND",e.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> handleUsernameNotFoundException(UsernameNotFoundException e){
        return new ResponseEntity<>(new AppError("USERNAME_NOT_FOUND",e.getMessage()),
                HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler
    public ResponseEntity<AppError> handleBadCredentialsException(BadCredentialsException e){
        return new ResponseEntity<>(
                new AppError("CHECK_USERNAME_PASSWORD_ERROR", "Некорректный логин или пароль"),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> handleInputDataErrorException(InputDataErrorException e){
        return new ResponseEntity<>(new AppError("INPUT_DATA_ERROR",
                e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> handleNotDeletedUserException(NotDeletedUserException e){
        return new ResponseEntity<>(new AppError("USER_ALREADY_EXISTS",
                e.getMessage()), HttpStatus.BAD_REQUEST);
    }

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

    @ExceptionHandler
    public ResponseEntity<AppError> handlePublicKeyErrorException(PublicKeyErrorException e){
        return new ResponseEntity<>(new AppError("PUBLIC_KEY_ERROR",
                e.getMessage()), HttpStatus.FORBIDDEN);
    }




}
