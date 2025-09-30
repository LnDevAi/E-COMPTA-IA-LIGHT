package com.ecomptaia.accounting.service.financial;

import com.ecomptaia.accounting.entity.financial.CompteResultat;
import java.time.LocalDate;

public interface CompteResultatService {
    CompteResultat genererCompteResultat(LocalDate dateDebut, LocalDate dateFin);
}
