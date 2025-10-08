package com.ecomptaia.accounting.ocr;

import org.springframework.stereotype.Service;

@Service
public class SimpleOcrService implements OcrService {
    @Override
    public String extractText(byte[] content, String filename) {
        // Placeholder OCR: integrate Tesseract/Cloud Vision in production
        return "[OCR] Extracted text from " + filename + " (size=" + content.length + ")";
    }
}
