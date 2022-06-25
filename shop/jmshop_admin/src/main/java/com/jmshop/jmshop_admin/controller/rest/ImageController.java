package com.jmshop.jmshop_admin.controller.rest;

import com.jmshop.jmshop_admin.util.FtpUtil;
import com.jmshop.jmshop_admin.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/register")
@RequiredArgsConstructor
public class ImageController {
    private final FtpUtil ftpUtil;

    @PostMapping(path = "/image")
    public ResponseEntity<String> registerImage(@RequestParam("banner_image") MultipartFile multipartFile
            , @RequestParam Map<String, String> paramMap) throws Exception {
        if (multipartFile.getOriginalFilename().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
        }
        String imgSrc = ftpUtil.ftpFileUpload(Util.getStaticFileUUID(multipartFile), multipartFile.getInputStream(), paramMap.get("dest").equals("admin"));
        return ResponseEntity.status(HttpStatus.OK).body(imgSrc);
    }

    @PostMapping(path = "/images")
    public ResponseEntity<List<String>> registerImages(@RequestParam("product_image") List<MultipartFile> multipartFiles
            , @RequestParam Map<String, String> paramMap) throws Exception {
        List<String> imgSrcList = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            if (multipartFile.getOriginalFilename().isEmpty()) {
                continue;
            }
            String imgSrc = ftpUtil.ftpFileUpload(Util.getStaticFileUUID(multipartFile), multipartFile.getInputStream(), paramMap.get("dest").equals("admin"));
            imgSrcList.add(imgSrc);
        }
        return ResponseEntity.status(HttpStatus.OK).body(imgSrcList);
    }
}
