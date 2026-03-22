package com.smartcv.builder.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class StorageService {

    @Value("${upload.path:uploads}")
    private String uploadPath;

    public String store(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("Failed to store empty file.");
        }
        
        Path root = Paths.get(uploadPath);
        if (!Files.exists(root)) {
            Files.createDirectories(root);
        }

        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Files.copy(file.getInputStream(), root.resolve(filename));
        
        return filename;
    }

    public Path load(String filename) {
        return Paths.get(uploadPath).resolve(filename);
    }
}
