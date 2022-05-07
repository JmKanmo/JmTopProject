package com.jmshop.jmshop_admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class JmShopAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(JmShopAdminApplication.class, args);
    }
}
