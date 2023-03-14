package com.jmshop.jmshop_admin.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jmshop.jmshop_admin.dto.error.type.JsonException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtil {
    public static JsonObject getJsonObject(String str) throws JsonException {
        try {
            return JsonParser.parseString(str).getAsJsonObject();
        } catch (RuntimeException exception) {
            log.error("JsonUtil:getJsonObject: exception:", exception);
            throw new JsonException("JSON 문자열로 변환 중에 문제가 발생하였습니다.");
        }
    }
}
