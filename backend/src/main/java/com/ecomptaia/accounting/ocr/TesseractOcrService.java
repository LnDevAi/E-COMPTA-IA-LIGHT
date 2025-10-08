package com.ecomptaia.accounting.ocr;

import org.springframework.stereotype.Service;

@Service
public class TesseractOcrService implements OcrService {
    public boolean isAvailable() { return false; }
    @Override
    public String extractText(byte[] content, String filename) {
        throw new UnsupportedOperationException("Tesseract not configured");
    }
}
