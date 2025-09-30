package com.ecomptaia.accounting.service.financial.impl;

import com.ecomptaia.accounting.entity.financial.Bilan;
import com.ecomptaia.accounting.service.financial.BilanService;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class BilanServiceImpl implements BilanService {
    @Override
    public Bilan genererBilan(LocalDate dateDebut, LocalDate dateFin) {
        // TODO: Implémenter la logique de génération du bilan à partir des écritures comptables
        return new Bilan();
    }
}
