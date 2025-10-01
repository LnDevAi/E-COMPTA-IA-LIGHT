package com.ecomptaia.accounting.service.financial;

import com.ecomptaia.accounting.entity.Entreprise;
import com.ecomptaia.accounting.entity.TypeSystemeOhada;
import com.ecomptaia.accounting.entity.financial.EtatFinancierOhada;
import com.ecomptaia.accounting.entity.financial.NoteAnnexe;
import com.ecomptaia.accounting.service.financial.impl.BilanServiceImpl;
import com.ecomptaia.accounting.service.financial.impl.CompteResultatServiceImpl;
import com.ecomptaia.accounting.service.financial.impl.NoteAnnexeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.UUID;
import java.util.Collections;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class EtatFinancierOhadaServiceNotesTest {
    @Mock
    private BilanServiceImpl bilanService;
    @Mock
    private CompteResultatServiceImpl compteResultatService;
    @Mock
    private NoteAnnexeServiceImpl noteAnnexeService;
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
    void testNotesAnnexesNormal() {
        NoteAnnexe note = new NoteAnnexe();
        note.setTitre("Méthodes comptables");
        note.setContenu("Contenu réglementaire");
        when(noteAnnexeService.genererNotesAnnexes("NORMAL")).thenReturn(Collections.singletonList(note));
        EtatFinancierOhada etat = etatFinancierOhadaService.genererEtatsPourEntreprise(entrepriseNormal, "2025");
        assertNotNull(etat.getNoteAnnexe());
        assertEquals("Méthodes comptables", etat.getNoteAnnexe().getTitre());
    }

    @Test
    void testNotesAnnexesMinimal() {
        NoteAnnexe note = new NoteAnnexe();
        note.setTitre("Informations diverses");
        note.setContenu("Contenu simplifié");
        when(noteAnnexeService.genererNotesAnnexes("MINIMAL")).thenReturn(Collections.singletonList(note));
        EtatFinancierOhada etat = etatFinancierOhadaService.genererEtatsPourEntreprise(entrepriseMinimal, "2025");
        assertNotNull(etat.getNoteAnnexe());
        assertEquals("Informations diverses", etat.getNoteAnnexe().getTitre());
    }
}
