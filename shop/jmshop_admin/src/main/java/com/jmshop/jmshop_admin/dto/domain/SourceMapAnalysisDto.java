package com.jmshop.jmshop_admin.dto.domain;

import com.jmshop.jmshop_admin.util.sourcemap.mapping.SourceMapping;
import lombok.Data;

import java.util.Map;

@Data
public class SourceMapAnalysisDto {
    private String fileName;
    private int sourceLine;
    private int sourceColumn;
    private String sourceCode;

    public static SourceMapAnalysisDto from(SourceMapping sourceMapping, Map<String, String> sourceContentMap) {
        SourceMapAnalysisDto sourceMapAnalysisDto = new SourceMapAnalysisDto();
        sourceMapAnalysisDto.fileName = sourceMapping.getSourceFileName();
        sourceMapAnalysisDto.sourceLine = sourceMapping.getSourceLine();
        sourceMapAnalysisDto.sourceColumn = sourceMapping.getSourceColumn();
        sourceMapAnalysisDto.sourceCode = sourceContentMap.get(sourceMapping.getSourceFileName());
        return sourceMapAnalysisDto;
    }
}
