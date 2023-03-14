package com.jmshop.jmshop_admin.util;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FtpSourceMappingUtilTest {
    @Autowired
    private FtpUtil ftpUtil;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void ftpInitTest() {
        try {
            assertNotNull(ftpUtil);
        } catch (Exception e) {
            logger.debug("[JmShopAdmin] FtpUtil:ftpFileUpload => ", e);
            fail();
        }
    }

    @Test
    public void ftpFileUploadTest() {
        try {
            String filename = "찡그린덕구.GIF";
            String UUID_filename = UUID.nameUUIDFromBytes(filename.getBytes()).toString() + ".GIF";
            String directory = "D:\\" + filename;
            File file = new File(directory); // 로컬 파일 경로
            ftpUtil.ftpFileUpload(UUID_filename, file);
        } catch (Exception e) {
            logger.debug("[JmShopAdmin] FtpUtil:ftpFileUpload => ", e);
            fail();
        }
    }
}