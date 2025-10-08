package com.ecomptaia.accounting.audit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditService {
    @Autowired
    private AuditLogRepository repo;

    public void log(String action, String details) {
        AuditLog l = new AuditLog();
        l.setAction(action);
        l.setDetails(details);
        repo.save(l);
    }
}
