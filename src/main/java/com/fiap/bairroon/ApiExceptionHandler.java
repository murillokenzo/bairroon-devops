package com.fiap.bairroon;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> conflito() {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Operacao viola a integridade: existem registros relacionados.");
    }
}
