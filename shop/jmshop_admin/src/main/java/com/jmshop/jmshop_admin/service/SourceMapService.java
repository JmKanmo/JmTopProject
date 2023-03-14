package com.jmshop.jmshop_admin.service;

import com.jmshop.jmshop_admin.dto.error.type.SourceMapException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface SourceMapService {
    void uploadSourceMap(long pcode, String version, String host, List<MultipartFile> sourceMapList) throws Exception;
}
