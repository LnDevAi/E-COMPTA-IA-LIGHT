package com.ecomptaia.accounting.ocr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompositeOcrService implements OcrService {
    @Autowired(required = false)
    private TesseractOcrService tesseract;

    @Autowired
    private SimpleOcrService simple;

    @Override
    public String extractText(byte[] content, String filename) {
        if (tesseract != null && tesseract.isAvailable()) {
            try { return tesseract.extractText(content, filename); } catch (Exception ignore) {}
        }
        return simple.extractText(content, filename);
    }
}
