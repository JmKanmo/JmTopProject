package com.service.jmshop;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/userinfo")
public class UserInfoController {
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@ModelAttribute UserInfo userInfo, @RequestPart MultipartFile product_image) {
        return ResponseEntity.ok().body(userInfo);
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public ResponseEntity<?> handleForm(@RequestParam("firstName") String firstName,
                                        @RequestParam("firstName") String lastName) {
        return ResponseEntity.ok().body(firstName);
    }
}
