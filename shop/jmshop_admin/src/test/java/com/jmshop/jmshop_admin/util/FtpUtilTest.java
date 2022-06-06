package com.jmshop.jmshop_admin.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FtpUtilTest {
    @Autowired
    private FtpUtil ftpUtil;

    @Test
    public void ftpConnectAndDisConnectTest() {
        try {
            assertNotNull(ftpUtil);
            ftpUtil.connect();
            ftpUtil.disconnect();
        } catch (Exception e) {
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
            assertEquals(ftpUtil.isFtpConnected(),false);
        } catch (Exception e) {
            fail();
        }
    }
}