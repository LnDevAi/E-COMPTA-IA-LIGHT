package com.ecomptaia.accounting.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class LocalFileStorageService implements FileStorageService {
    @Value("${storage.local.baseDir:/workspace/storage}")
    private String baseDir;

    @Override
    public String store(MultipartFile file, String subdirectory) {
        try {
            String filename = UUID.randomUUID() + "-" + StringUtils.cleanPath(file.getOriginalFilename());
            Path dir = Paths.get(baseDir, subdirectory).toAbsolutePath();
            Files.createDirectories(dir);
            Path target = dir.resolve(filename);
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
            return target.toString();
        } catch (IOException e) {
            throw new RuntimeException("File storage failed", e);
        }
    }

    @Override
    public byte[] read(String absolutePath) {
        try {
            return Files.readAllBytes(Paths.get(absolutePath));
        } catch (IOException e) {
            throw new RuntimeException("File read failed", e);
        }
    }
}
