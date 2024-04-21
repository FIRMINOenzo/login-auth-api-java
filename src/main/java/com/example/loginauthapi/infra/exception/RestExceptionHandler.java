package com.example.loginauthapi.infra.exception;

import com.example.loginauthapi.dtos.error.RestErrorMessageDTO;
import com.example.loginauthapi.exceptions.EmailAlreadyInUseException;
import com.example.loginauthapi.exceptions.UserNotFoundException;
import com.example.loginauthapi.exceptions.WrongPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<RestErrorMessageDTO> userNotFoundHandler(UserNotFoundException exception) {
        RestErrorMessageDTO errorMessage = new RestErrorMessageDTO(HttpStatus.NOT_FOUND, true, exception.getMessage());

        return ResponseEntity.status(errorMessage.status()).body(errorMessage);
    }

    @ExceptionHandler(WrongPasswordException.class)
    private ResponseEntity<RestErrorMessageDTO> wrongPasswordException(WrongPasswordException exception) {
        RestErrorMessageDTO errorMessage = new RestErrorMessageDTO(HttpStatus.UNAUTHORIZED, true, exception.getMessage());

        return ResponseEntity.status(errorMessage.status()).body(errorMessage);
    }

    @ExceptionHandler(EmailAlreadyInUseException.class)
    private ResponseEntity<RestErrorMessageDTO> emailAlreadyInUseException (EmailAlreadyInUseException exception) {
        RestErrorMessageDTO errorMessage = new RestErrorMessageDTO(HttpStatus.OK, true, exception.getMessage());

        return ResponseEntity.status(errorMessage.status()).body(errorMessage);
    }
}
