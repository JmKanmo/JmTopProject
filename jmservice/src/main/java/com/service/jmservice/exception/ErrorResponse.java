package com.service.jmservice.exception;

import lombok.Data;

@Data
public class ErrorResponse {
    private String statusCode;
    private String requestUrl;
    private String message;
    private String resultCode;

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "statusCode='" + statusCode + '\'' +
                ", requestUrl='" + requestUrl + '\'' +
                ", message='" + message + '\'' +
                ", resultCode='" + resultCode + '\'' +
                '}';
    }
}
