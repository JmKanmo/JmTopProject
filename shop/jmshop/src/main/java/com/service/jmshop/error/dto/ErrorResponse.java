package com.service.jmshop.error.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ErrorResponse {
    @NotNull
    private String statusCode;
    @NotNull
    private String requestUrl;
    @NotNull
    private String code;
    @NotNull
    private String message;
    @NotNull
    private String resultCode;
    @NotNull
    private List<Error> errorList;
}
