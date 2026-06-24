package com.bfhl.api.exception;

import com.bfhl.api.dto.BfhlResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BfhlResponseDto> handleAllExceptions(Exception ex) {
        BfhlResponseDto errorResponse = BfhlResponseDto.builder()
                .isSuccess(false)
                .userId("error_user")
                .email("error@xyz.com")
                .rollNumber("ERROR")
                .evenNumbers(Collections.emptyList())
                .oddNumbers(Collections.emptyList())
                .alphabets(Collections.emptyList())
                .specialCharacters(Collections.emptyList())
                .sepcialCharacters(Collections.emptyList())
                .sum("0")
                .concatString("")
                .build();
        return ResponseEntity.ok(errorResponse);
    }
}
