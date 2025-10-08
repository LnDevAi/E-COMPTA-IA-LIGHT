package com.ecomptaia.accounting.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    String store(MultipartFile file, String subdirectory);
    byte[] read(String absolutePath);
}
