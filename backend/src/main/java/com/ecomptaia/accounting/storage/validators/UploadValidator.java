package com.ecomptaia.accounting.storage.validators;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class UploadValidator {
    private static final long MAX_SIZE_BYTES = 10 * 1024 * 1024; // 10MB
    private static final String[] ALLOWED = new String[]{"application/pdf","image/png","image/jpeg"};

    public void validate(MultipartFile file) {
        if (file.getSize() > MAX_SIZE_BYTES) {
            throw new RuntimeException("File too large");
        }
        String ct = file.getContentType();
        boolean ok = false;
        if (ct != null) {
            for (String a : ALLOWED) if (ct.equalsIgnoreCase(a)) { ok = true; break; }
        }
        if (!ok) {
            throw new RuntimeException("Unsupported content type");
        }
    }
}
