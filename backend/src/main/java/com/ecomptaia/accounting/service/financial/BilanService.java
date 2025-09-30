package com.ecomptaia.accounting.service.financial;

import com.ecomptaia.accounting.entity.financial.Bilan;
import java.time.LocalDate;

public interface BilanService {
    Bilan genererBilan(LocalDate dateDebut, LocalDate dateFin);
}
