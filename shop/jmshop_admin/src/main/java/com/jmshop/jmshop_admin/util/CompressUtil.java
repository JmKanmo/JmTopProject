package com.jmshop.jmshop_admin.util;

import com.jmshop.jmshop_admin.dto.error.type.CompressException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

@Component
@Slf4j
@RequiredArgsConstructor
public class CompressUtil {
    public byte[] compressData(byte[] data) throws CompressException {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream)) {
            gzipOutputStream.write(data);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            log.error("CompressUtil:compressData: exception:", e);
            throw new CompressException("압축 중에 에러가 발생하였습니다.");
        }
    }

    public byte[] deCompressData(byte[] data) throws CompressException {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
             GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream)) {
            return gzipInputStream.readAllBytes();
        } catch (Exception e) {
            log.error("CompressUtil:deCompressData: exception:", e);
            throw new CompressException("압축해제 중에 에러가 발생하였습니다.");
        }
    }
}
