package com.jmshop.jmshop_admin.service.impl;

import com.jmshop.jmshop_admin.dto.error.type.SourceMapException;
import com.jmshop.jmshop_admin.service.SourceMapService;
import com.jmshop.jmshop_admin.util.sourcemap.SourceMapUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SourceMapServiceImpl implements SourceMapService {
    private final SourceMapUtil sourceMapUtil;

    @Override
    public void uploadSourceMap(long pcode, String version, String host, List<MultipartFile> sourceMapList) throws Exception {
        try {
            sourceMapUtil.uploadSourceMap(pcode, version, host, sourceMapList);
        } catch (Exception e) {
            throw e;
        }
    }
}
