package com.jmshop.jmshop_admin.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class FtpAdminConfig {
    @Value("${admin_ftp.ip}")
    private String ip;

    @Value("${admin_ftp.port}")
    private Integer port;

    @Value("${admin_ftp.id}")
    private String id;

    @Value("${admin_ftp.password}")
    private String password;

    @Value("${admin_ftp.directory}")
    private String directory;

    @Value("${admin_ftp.timeout}")
    private Integer timeout;

    @Value("${admin_ftp.encoding}")
    private String encoding;
}
