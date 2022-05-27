package com.service.jmshop.dto.error;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Error {
    @NotNull
    private String field;
    @NotNull
    private String message;
    @NotNull
    private String invalidValue;
}
