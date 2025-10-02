package com.ecomptaia.accounting.module.iaec;

import com.ecomptaia.accounting.entity.PieceComptableGed;
import com.ecomptaia.accounting.entity.EcritureComptable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IaecService {
    @Autowired
    private IaecRepository iaecRepository;

    public Optional<PieceComptableGed> getPiece(Long id) {
        return iaecRepository.findById(id);
    }

    // Simule l'analyse IA et propose une écriture comptable
    public EcritureComptable proposeEcriture(PieceComptableGed piece) {
        // TODO: Intégrer le moteur IA réel
        EcritureComptable ecriture = new EcritureComptable();
        ecriture.setLibelle("Proposition IA pour " + piece.getOriginalFilename());
        // ... autres champs à compléter
        return ecriture;
    }
}
