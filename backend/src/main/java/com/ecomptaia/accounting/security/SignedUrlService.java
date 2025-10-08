package com.ecomptaia.accounting.security;

import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.Base64;

@Service
public class SignedUrlService {
    private static final long EXPIRY_SECONDS = 300; // 5 minutes

    public String sign(String fileId) {
        long exp = Instant.now().getEpochSecond() + EXPIRY_SECONDS;
        String token = fileId + ":" + exp;
        return Base64.getUrlEncoder().withoutPadding().encodeToString(token.getBytes());
    }

    public boolean verify(String token, String expectedFileId) {
        try {
            String decoded = new String(Base64.getUrlDecoder().decode(token));
            String[] parts = decoded.split(":");
            if (parts.length != 2) return false;
            if (!expectedFileId.equals(parts[0])) return false;
            long exp = Long.parseLong(parts[1]);
            return Instant.now().getEpochSecond() <= exp;
        } catch (Exception e) {
            return false;
        }
    }
}
