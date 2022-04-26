package com.service.jmshop;

import lombok.Data;

@Data
public class UserInfo {
    private String firstName;
    private String lastName;
    private String role;
    private byte[] image;
    //getters and setters
}