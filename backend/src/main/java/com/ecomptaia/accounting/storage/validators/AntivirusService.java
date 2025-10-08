package com.ecomptaia.accounting.storage.validators;

import org.springframework.stereotype.Component;

@Component
public class AntivirusService {
    public void scan(byte[] content) {
        // Placeholder for ClamAV/ICAP integration
        if (content == null || content.length == 0) {
            throw new RuntimeException("Empty file");
        }
    }
}
