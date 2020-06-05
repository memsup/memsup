package com.github.memsup.profile;

import com.github.memsup.profile.exception.ProfileNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.concurrent.CompletionException;

@ControllerAdvice(assignableTypes = {ProfileController.class})
@Slf4j
public class ProfileControllerAdvice {

    @ExceptionHandler(ProfileNotFoundException.class)
    public ResponseEntity<String> handleProfileNotFoundException(ProfileNotFoundException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>("Profile not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleProfileNotFoundException(NullPointerException e) {
        log.error(e.getMessage());
        System.out.println("buradaNulll");
        return new ResponseEntity<>("Profile not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CompletionException.class)
    public ResponseEntity<String> handleProfileNotFoundException(CompletionException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>("Profile not found", HttpStatus.NOT_FOUND);
    }

}
