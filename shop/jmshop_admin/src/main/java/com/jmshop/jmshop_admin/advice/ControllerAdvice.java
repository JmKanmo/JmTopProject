package com.jmshop.jmshop_admin.advice;

import com.jmshop.jmshop_admin.dto.error.Error;
import com.jmshop.jmshop_admin.dto.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@RestControllerAdvice(basePackages = "com.jmshop.jmshop_admin.controller")
public class ControllerAdvice {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> exceptionHandler(Exception e, HttpServletRequest httpServletRequest) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("error cause: " + e.getCause());
        stringBuilder.append(" , ");
        stringBuilder.append("error message: " + e.getMessage());

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorList(new ArrayList<>());
        errorResponse.setMessage(stringBuilder.toString());
        errorResponse.setRequestUrl(httpServletRequest.getRequestURI());
        errorResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        errorResponse.setResultCode("FAIL");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(value = BindException.class)
    public ResponseEntity<?> bindException(BindException e, HttpServletRequest httpServletRequest) {
        List<Error> errorList = new ArrayList<>();

        BindingResult bindingResult = e.getBindingResult();
        bindingResult.getAllErrors().forEach(error -> {
            FieldError field = (FieldError) error;

            Optional<String> fieldName = Optional.ofNullable(field.getField());
            Optional<String> message = Optional.ofNullable(field.getDefaultMessage());
            Optional<Object> value = Optional.ofNullable(field.getRejectedValue());

            Error errorMessage = new Error();
            errorMessage.setField(fieldName.orElse("<<undefine>>"));
            errorMessage.setMessage(message.orElse("<<undefine>>"));
            errorMessage.setInvalidValue(value.isPresent() ? value.get().toString() : "<<undefine>>");
            errorList.add(errorMessage);
        });

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorList(errorList);
        errorResponse.setMessage("");
        errorResponse.setRequestUrl(httpServletRequest.getRequestURI());
        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.toString());
        errorResponse.setResultCode("FAIL");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest httpServletRequest) {
        List<Error> errorList = new ArrayList<>();

        BindingResult bindingResult = e.getBindingResult();
        bindingResult.getAllErrors().forEach(error -> {
            FieldError field = (FieldError) error;

            Optional<String> fieldName = Optional.ofNullable(field.getField());
            Optional<String> message = Optional.ofNullable(field.getDefaultMessage());
            Optional<Object> value = Optional.ofNullable(field.getRejectedValue());

            Error errorMessage = new Error();
            errorMessage.setField(fieldName.orElse("<<undefine>>"));
            errorMessage.setMessage(message.orElse("<<undefine>>"));
            errorMessage.setInvalidValue(value.isPresent() ? value.get().toString() : "<<undefine>>");
            errorList.add(errorMessage);
        });

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorList(errorList);
        errorResponse.setMessage("");
        errorResponse.setRequestUrl(httpServletRequest.getRequestURI());
        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.toString());
        errorResponse.setResultCode("FAIL");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<?> constraintViolationException(ConstraintViolationException e, HttpServletRequest httpServletRequest) {
        List<Error> errorList = new ArrayList<>();

        e.getConstraintViolations().forEach(error -> {
            Stream<Path.Node> stream = StreamSupport.stream(error.getPropertyPath().spliterator(), false);
            List<Path.Node> list = stream.collect(Collectors.toList());

            Optional<String> fieldName = Optional.ofNullable(list.get(list.size() - 1).getName());
            Optional<String> message = Optional.ofNullable(error.getMessage());
            Optional<Object> value = Optional.ofNullable(error.getInvalidValue());

            Error errorMessage = new Error();
            errorMessage.setField(fieldName.orElse("<<undefine>>"));
            errorMessage.setMessage(message.orElse("<<undefine>>"));
            errorMessage.setInvalidValue(value.isPresent() ? value.get().toString() : "<<undefine>>");

            errorList.add(errorMessage);
        });

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorList(errorList);
        errorResponse.setMessage("");
        errorResponse.setRequestUrl(httpServletRequest.getRequestURI());
        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.toString());
        errorResponse.setResultCode("FAIL");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
