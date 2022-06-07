package com.jmshop.jmshop_admin.util;


import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class Util {
    public static Optional ofEmpty() {
        return Optional.empty();
    }

    public static String getStaticFileUUID(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        String uuid = LocalDateTime.now() + "_product-image_" + UUID.nameUUIDFromBytes(fileName.getBytes(StandardCharsets.UTF_8)) + "." + extension;
        return uuid;
    }

    public static File transferTo(MultipartFile multipartFile) throws IOException {
        File file = new File(System.getProperty("user.dir") + "/static_files/" + multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);
        file.createNewFile();
        return file;
    }
}
