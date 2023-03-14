package com.jmshop.jmshop_admin.controller.rest;


import com.jmshop.jmshop_admin.service.SourceMapService;
import com.jmshop.jmshop_admin.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class SourceMapController {
    private final SourceMapService sourceMapService;

    // TODO
    @PostMapping(path = "/project/api/pcode/{pcode}/sourcemap/data")
    public ResponseEntity<String> registerImages(@PathVariable("pcode") long pcode, @RequestParam("sourcemap") List<MultipartFile> sourceMapList,
                                                 @RequestParam(value = "version", defaultValue = "", required = false) String version,
                                                 @RequestParam(value = "host", defaultValue = "", required = false) String host) throws Exception {
        try {
            sourceMapService.uploadSourceMap(pcode, version, host, sourceMapList);
            return ResponseEntity.status(HttpStatus.OK).body("업로드가 되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("에러가 발생하였습니다: " + e.getMessage());
        }
    }
}
