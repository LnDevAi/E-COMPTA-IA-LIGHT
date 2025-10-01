package com.ecomptaia.accounting.service.financial;

import com.ecomptaia.accounting.entity.Entreprise;
import com.ecomptaia.accounting.entity.TypeSystemeOhada;
import com.ecomptaia.accounting.entity.financial.EtatFinancierOhada;
import com.ecomptaia.accounting.entity.financial.Bilan;
import com.ecomptaia.accounting.entity.financial.CompteResultat;
import com.ecomptaia.accounting.service.financial.impl.BilanServiceImpl;
import com.ecomptaia.accounting.service.financial.impl.CompteResultatServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.UUID;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class EtatFinancierOhadaServiceTest {
    @Mock
    private BilanServiceImpl bilanService;
    @Mock
    private CompteResultatServiceImpl compteResultatService;
    @Mock
    private com.ecomptaia.accounting.service.financial.NoteAnnexeService noteAnnexeService;
    @InjectMocks
    private EtatFinancierOhadaService etatFinancierOhadaService;

    private Entreprise entrepriseNormal;
    private Entreprise entrepriseMinimal;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        entrepriseNormal = new Entreprise();
        entrepriseNormal.setId(UUID.randomUUID());
        entrepriseNormal.setNom("Test SA");
        entrepriseNormal.setPays("CI");
        entrepriseNormal.setTypeSystemeOhada(TypeSystemeOhada.NORMAL);

        entrepriseMinimal = new Entreprise();
        entrepriseMinimal.setId(UUID.randomUUID());
        entrepriseMinimal.setNom("Petite SARL");
        entrepriseMinimal.setPays("CI");
        entrepriseMinimal.setTypeSystemeOhada(TypeSystemeOhada.MINIMAL);
    }

    @Test
    void testGenererEtatsNormalAvecCalcul() {
        Bilan bilan = new Bilan();
        bilan.setTotalActif(1000.0);
        bilan.setTotalPassif(1000.0);
        CompteResultat resultat = new CompteResultat();
        resultat.setTotalProduits(500.0);
        resultat.setTotalCharges(400.0);
        resultat.setResultatNet(100.0);
        when(bilanService.genererBilan(any(), any())).thenReturn(bilan);
        when(compteResultatService.genererCompteResultat(any(), any())).thenReturn(resultat);
        when(noteAnnexeService.genererNotesAnnexes("NORMAL")).thenReturn(java.util.Collections.emptyList());
        EtatFinancierOhada etat = etatFinancierOhadaService.genererEtatsPourEntreprise(entrepriseNormal, "2025");
        assertEquals(1000.0, etat.getBilan().getTotalActif());
        assertEquals(1000.0, etat.getBilan().getTotalPassif());
        assertEquals(500.0, etat.getCompteResultat().getTotalProduits());
        assertEquals(400.0, etat.getCompteResultat().getTotalCharges());
        assertEquals(100.0, etat.getCompteResultat().getResultatNet());
    }

    @Test
    void testGenererEtatsMinimal() {
    when(noteAnnexeService.genererNotesAnnexes("MINIMAL")).thenReturn(java.util.Collections.emptyList());
    EtatFinancierOhada etat = etatFinancierOhadaService.genererEtatsPourEntreprise(entrepriseMinimal, "2025");
    assertNotNull(etat.getTableauTresorerie());
    assertEquals("2025", etat.getTableauTresorerie().getExercice());
    }
}
