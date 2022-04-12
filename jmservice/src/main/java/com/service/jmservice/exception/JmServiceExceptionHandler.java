package com.service.jmservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class JmServiceExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity catchException(Exception e, HttpServletRequest httpServletRequest) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(e.toString());
        errorResponse.setRequestUrl(httpServletRequest.getRequestURI());
        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.toString());
        errorResponse.setResultCode("ERROR-FAIL");
        log.error("[JmService - JmServiceExceptionHandler], Error Happened => " + errorResponse);
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(errorResponse);
    }
}
