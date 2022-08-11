package com.jmshop.jmshop_admin.util;

import com.jmshop.jmshop_admin.config.FtpAdminConfig;
import com.jmshop.jmshop_admin.config.FtpTempConfig;
import lombok.RequiredArgsConstructor;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class FtpUtil {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final FtpAdminConfig ftpAdminConfig;

    private final FtpTempConfig ftpTempConfig;

    public void adminFTPConnect(FTPClient ftpClient) throws Exception {
        try {
            boolean result = false;
            ftpClient.connect(ftpAdminConfig.getIp(), ftpAdminConfig.getPort());
            int reply = ftpClient.getReplyCode();

            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                throw new FTPConnectionClosedException("Admin FTP Server connection failed");
            }
            if (!ftpClient.login(ftpAdminConfig.getId(), ftpAdminConfig.getPassword())) {
                ftpClient.logout();
                throw new Exception("Admin FTP Server login failed");
            }

            ftpClient.setSoTimeout(1000 * ftpAdminConfig.getTimeout());
            ftpClient.login(ftpAdminConfig.getId(), ftpAdminConfig.getPassword());
            ftpClient.setControlEncoding(ftpAdminConfig.getEncoding());
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
            ftpClient.enterLocalPassiveMode();
            result = ftpClient.changeWorkingDirectory(ftpAdminConfig.getDirectory());

            if (!result) {
                ftpClient.makeDirectory(ftpAdminConfig.getDirectory());
                ftpClient.changeWorkingDirectory(ftpAdminConfig.getDirectory());
            }
        } catch (Exception e) {
            if (e.getMessage().indexOf("refused") != -1) {
                throw new FTPConnectionClosedException("Admin FTP Server connection failed");
            }
            throw e;
        }
    }

    public void tempFTPConnect(FTPClient ftpClient) throws Exception {
        try {
            boolean result = false;
            ftpClient.connect(ftpTempConfig.getIp(), ftpTempConfig.getPort());
            int reply = ftpClient.getReplyCode();

            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                throw new FTPConnectionClosedException("Temp FTP Server connection failed");
            }
            if (!ftpClient.login(ftpTempConfig.getId(), ftpTempConfig.getPassword())) {
                ftpClient.logout();
                throw new Exception("Temp FTP Server login failed");
            }

            ftpClient.setSoTimeout(1000 * ftpTempConfig.getTimeout());
            ftpClient.login(ftpTempConfig.getId(), ftpTempConfig.getPassword());
            ftpClient.setControlEncoding(ftpTempConfig.getEncoding());
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
            ftpClient.enterLocalPassiveMode();
            result = ftpClient.changeWorkingDirectory(ftpTempConfig.getDirectory());

            if (!result) {
                ftpClient.makeDirectory(ftpTempConfig.getDirectory());
                ftpClient.changeWorkingDirectory(ftpTempConfig.getDirectory());
            }
        } catch (Exception e) {
            if (e.getMessage().indexOf("refused") != -1) {
                throw new FTPConnectionClosedException("Temp FTP Server connection failed");
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
            adminFTPConnect(ftpClient);
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

    public String fileUpload(String fileUUID, InputStream fileInputStream, boolean admin) throws Exception {
        try {
            final FTPClient ftpClient = new FTPClient();

            if (admin) {
                adminFTPConnect(ftpClient);
            } else {
                tempFTPConnect(ftpClient);
            }

            try {
                storeFile(ftpClient, fileUUID, fileInputStream);
            } catch (Exception e) {
                logger.debug("[JmShopAdmin] FtpUtil:ftpFileUpload => ", e);
                throw e;
            } finally {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            }

            disconnect(ftpClient);
            return admin ? "http://" + ftpAdminConfig.getIp() + "/" + fileUUID :
                    "http://" + ftpTempConfig.getIp() + "/" + fileUUID;
        } catch (Exception e) {
            throw e;
        }
    }
}
