package com.ecomptaia.accounting.service;

import com.ecomptaia.accounting.entity.Entreprise;
import com.ecomptaia.accounting.entity.SystemeComptable;
import com.ecomptaia.accounting.entity.TypeSystemeOhada;
import com.ecomptaia.accounting.repository.EntrepriseRepository;
import com.ecomptaia.accounting.repository.SystemeComptableRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.UUID;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class EntrepriseServiceTest {
    @Mock
    private EntrepriseRepository entrepriseRepository;
    @Mock
    private PlanComptableService planComptableService;
    @Mock
    private com.ecomptaia.accounting.service.financial.EtatFinancierOhadaService etatFinancierOhadaService;
    @InjectMocks
    private EntrepriseService entrepriseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreerEntrepriseAutomatique_OHADA_Normal() {
        SystemeComptable systeme = new SystemeComptable();
        systeme.setCode("OHADA");
        Entreprise ent = new Entreprise();
        ent.setId(UUID.randomUUID());
        ent.setNom("Test SA");
        ent.setPays("CI");
        ent.setSystemeComptable(systeme);
        ent.setTypeSystemeOhada(TypeSystemeOhada.NORMAL);
        when(entrepriseRepository.save(any())).thenReturn(ent);
        when(planComptableService.getPlanComptableBySysteme("OHADA")).thenReturn(java.util.Collections.emptyList());
        Entreprise result = entrepriseService.creerEntrepriseAutomatique(ent);
        assertEquals("Test SA", result.getNom());
        verify(etatFinancierOhadaService).genererEtatsPourEntreprise(ent, "2025");
    }

    @Test
    void testCreerEntrepriseAutomatique_OHADA_Minimal() {
        SystemeComptable systeme = new SystemeComptable();
        systeme.setCode("OHADA");
        Entreprise ent = new Entreprise();
        ent.setId(UUID.randomUUID());
        ent.setNom("Petite SARL");
        ent.setPays("CI");
        ent.setSystemeComptable(systeme);
        ent.setTypeSystemeOhada(TypeSystemeOhada.MINIMAL);
        when(entrepriseRepository.save(any())).thenReturn(ent);
        when(planComptableService.getPlanComptableBySysteme("OHADA")).thenReturn(java.util.Collections.emptyList());
        Entreprise result = entrepriseService.creerEntrepriseAutomatique(ent);
        assertEquals("Petite SARL", result.getNom());
        verify(etatFinancierOhadaService).genererEtatsPourEntreprise(ent, "2025");
    }

    @Test
    void testCreerEntrepriseAutomatique_FR() {
        SystemeComptable systeme = new SystemeComptable();
        systeme.setCode("FR");
        Entreprise ent = new Entreprise();
        ent.setId(UUID.randomUUID());
        ent.setNom("SAS France");
        ent.setPays("FR");
        ent.setSystemeComptable(systeme);
        when(entrepriseRepository.save(any())).thenReturn(ent);
        when(planComptableService.getPlanComptableBySysteme("FR")).thenReturn(java.util.Collections.emptyList());
        Entreprise result = entrepriseService.creerEntrepriseAutomatique(ent);
        assertEquals("SAS France", result.getNom());
        verify(etatFinancierOhadaService, never()).genererEtatsPourEntreprise(any(), any());
    }
}
