package com.bitee.event.Exceptions;

import com.bitee.event.utils.ApiResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.security.SignatureException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiResponse<List<String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, List<String>> body = new HashMap<>();
        List<String> errors = new ArrayList<>();
        System.out.println("inside validation exception 500");
        ex.getBindingResult().getAllErrors().forEach((error) -> {
//            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.add(errorMessage);
        });
        body.put("errors", errors);
        ApiResponse response = ApiResponse.error("400", "Invalid fields", errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApiResponse<Object>> handleAllExceptions(Exception exception) {

        if (exception instanceof BadCredentialsException) {
            ApiResponse<Object> response = ApiResponse.error("400", "Invalid Credentials", null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if (exception instanceof SignatureException) {
            return new ResponseEntity<>(ApiResponse.error("403", "Invalid JWT Signature", null), HttpStatus.BAD_REQUEST);
        }

        if (exception instanceof ExpiredJwtException) {
            return new ResponseEntity<>(ApiResponse.error("400", "Expired Token", null), HttpStatus.BAD_REQUEST);
        }
        ApiResponse<Object> response = ApiResponse.error("500", "An unexpected error occurred", null);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
