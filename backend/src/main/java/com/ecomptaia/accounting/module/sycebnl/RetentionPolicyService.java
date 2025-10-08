package com.ecomptaia.accounting.module.sycebnl;

import com.ecomptaia.accounting.module.sycebnl.entity.PieceJustificativeSycebnl;
import com.ecomptaia.accounting.module.sycebnl.repository.PieceJustificativeSycebnlRepository;
import com.ecomptaia.accounting.storage.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class RetentionPolicyService {
    @Autowired
    private PieceJustificativeSycebnlRepository pieceRepository;
    @Autowired
    private FileStorageService storage;

    // Daily retention cleanup placeholder: keep last 365 days
    @Scheduled(cron = "0 0 3 * * *")
    public void cleanup() {
        var threshold = LocalDateTime.now().minusDays(365);
        for (PieceJustificativeSycebnl pj : pieceRepository.findAll()) {
            if (pj.getCreatedAt() != null && pj.getCreatedAt().isBefore(threshold)) {
                // In production: delete storage and anonymize metadata
                // storage.delete(pj.getFilePath()); // implement delete method when needed
            }
        }
    }
}
