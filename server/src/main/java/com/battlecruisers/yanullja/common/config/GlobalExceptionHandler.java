package com.battlecruisers.yanullja.common.config;

import com.battlecruisers.yanullja.common.exception.CustomValidationException;
import com.battlecruisers.yanullja.common.jsendresponse.JSendResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<JSendResponse> handleServerException(
        Exception ex) {
        log.error("Globally handling Exception! ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(JSendResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(CustomValidationException.class)
    protected ResponseEntity<JSendResponse> handleCustomValidationException(
        CustomValidationException ex) {
        log.error("CustomValidationException ", ex.getMessage());
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(JSendResponse.error(ex.getMessage()));
    }
}
