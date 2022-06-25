package com.jmshop.jmshop_admin.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class FtpTempConfig {
    // TODO
    @Value("${temp_ftp.ip}")
    private String ip;

    @Value("${temp_ftp.port}")
    private Integer port;

    @Value("${temp_ftp.id}")
    private String id;

    @Value("${temp_ftp.password}")
    private String password;

    @Value("${temp_ftp.directory}")
    private String directory;

    @Value("${temp_ftp.timeout}")
    private Integer timeout;

    @Value("${temp_ftp.encoding}")
    private String encoding;
}
