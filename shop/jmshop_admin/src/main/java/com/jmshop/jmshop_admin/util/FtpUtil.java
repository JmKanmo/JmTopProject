package com.jmshop.jmshop_admin.util;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class FtpUtil {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${ftp.ip}")
    private String ip;

    @Value("${ftp.port}")
    private Integer port;

    @Value("${ftp.id}")
    private String id;

    @Value("${ftp.password}")
    private String password;

    @Value("${ftp.directory}")
    private String directory;

    @Value("${ftp.timeout}")
    private Integer timeout;

    @Value("${ftp.encoding}")
    private String encoding;

    public void connect(FTPClient ftpClient) throws Exception {
        try {
            boolean result = false;
            ftpClient.connect(ip, port);
            int reply = ftpClient.getReplyCode();

            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                throw new FTPConnectionClosedException("FTP Server connection failed");
            }
            if (!ftpClient.login(id, password)) {
                ftpClient.logout();
                throw new Exception("FTP Server login failed");
            }

            ftpClient.setSoTimeout(1000 * timeout);
            ftpClient.login(id, password);
            ftpClient.setControlEncoding(encoding);
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
            ftpClient.enterLocalPassiveMode();
            result = ftpClient.changeWorkingDirectory(directory);

            if (!result) {
                ftpClient.makeDirectory(directory);
                ftpClient.changeWorkingDirectory(directory);
            }
        } catch (Exception e) {
            if (e.getMessage().indexOf("refused") != -1) {
                throw new FTPConnectionClosedException("FTP Server connection failed");
            }
            throw e;
        }
    }

    public void disconnect(FTPClient ftpClient) throws Exception {
        try {
            if (ftpClient.isConnected()) {
                ftpClient.disconnect();
            }
        } catch (IOException e) {
            int a = 0;
            int b = 0;
            a = b;
            b = a;
        } catch (Exception e) {
            throw e;
        }
    }

    public void storeFile(FTPClient ftpClient, String saveFileNm, InputStream inputStream) throws Exception {
        try {
            ftpClient.storeFile(saveFileNm, inputStream);
        } catch (Exception e) {
            throw e;
        }
    }

    public void ftpFileUpload(String fileUUID, File file) throws Exception {
        try {
            final FTPClient ftpClient = new FTPClient();
            connect(ftpClient);
            FileInputStream fis = new FileInputStream(file);

            try {
                fis = new FileInputStream(file);
                storeFile(ftpClient, fileUUID, fis);    //파일명
            } catch (Exception e) {
                logger.debug("[JmShopAdmin] FtpUtil:ftpFileUpload => ", e);
            } finally {
                if (fis != null) {
                    fis.close();
                }
            }
            disconnect(ftpClient);
        } catch (Exception e) {
            throw e;
        }
    }

    public void ftpFileUpload(String fileUUID, InputStream fileInputStream) throws Exception {
        try {
            final FTPClient ftpClient = new FTPClient();
            connect(ftpClient);

            try {
                storeFile(ftpClient, fileUUID, fileInputStream);    //파일명
            } catch (Exception e) {
                logger.debug("[JmShopAdmin] FtpUtil:ftpFileUpload => ", e);
            } finally {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            }
            disconnect(ftpClient);
        } catch (Exception e) {
            throw e;
        }
    }
}
