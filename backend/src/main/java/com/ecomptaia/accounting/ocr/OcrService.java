package com.ecomptaia.accounting.ocr;

public interface OcrService {
    String extractText(byte[] content, String filename);
}
