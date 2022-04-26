package com.jmshop.jmshop_admin.dto.error;

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
