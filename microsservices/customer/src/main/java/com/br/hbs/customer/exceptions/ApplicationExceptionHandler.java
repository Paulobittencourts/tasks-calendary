package com.br.hbs.customer.exceptions;

import com.br.hbs.customer.dto.DefaultMessageError;
import jakarta.persistence.EntityExistsException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.DateTimeException;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EntityExistsException.class})
    public ResponseEntity<Object> entityExistsException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new DefaultMessageError(HttpStatus.BAD_REQUEST.value(), "Could not find user"));
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> entityDuplicationException(Exception e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new DefaultMessageError(HttpStatus.CONFLICT.value(), e.getMessage()));
    }

    @ExceptionHandler({DateTimeException.class})
    public ResponseEntity<Object> dateTimeException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new DefaultMessageError(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

}
