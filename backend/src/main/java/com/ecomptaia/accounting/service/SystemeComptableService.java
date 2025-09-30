package com.ecomptaia.accounting.service;

import com.ecomptaia.accounting.entity.SystemeComptable;
import com.ecomptaia.accounting.repository.SystemeComptableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SystemeComptableService {
    private final SystemeComptableRepository systemeComptableRepository;

    @Transactional
    public SystemeComptable creerSysteme(SystemeComptable systeme) {
        return systemeComptableRepository.save(systeme);
    }

    public List<SystemeComptable> obtenirTousSystemes() {
        return systemeComptableRepository.findAll();
    }

    public SystemeComptable obtenirParCode(String code) {
        return systemeComptableRepository.findByCode(code);
    }
}
