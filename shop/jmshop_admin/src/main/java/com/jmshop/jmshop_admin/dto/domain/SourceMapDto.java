package com.jmshop.jmshop_admin.dto.domain;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.Data;

import java.util.Map;

@Data
public class SourceMapDto {
    private String fileName;
    private int version;
    private Map<String, String> sourceMap;

    public static SourceMapDto from(JsonObject jsonObject) {
        SourceMapDto sourceMapDto = new SourceMapDto();
        sourceMapDto.setFileName(jsonObject.get("file").getAsString());
        sourceMapDto.setVersion(jsonObject.get("version").getAsInt());

        JsonArray sourceJsonArr = jsonObject.get("sources").getAsJsonArray();
        JsonArray sourceContentsJsonArr = jsonObject.get("sourcesContent").getAsJsonArray();

        for (int i = 0; i < sourceJsonArr.size(); i++) {
            sourceMapDto.sourceMap.put(sourceJsonArr.get(i).getAsString(), sourceContentsJsonArr.get(i).getAsString());
        }
        return sourceMapDto;
    }
}
