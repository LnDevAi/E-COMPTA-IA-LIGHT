package com.ecomptaia.accounting.controller.financial;

import com.ecomptaia.accounting.entity.Entreprise;
import com.ecomptaia.accounting.entity.TypeSystemeOhada;
import com.ecomptaia.accounting.entity.financial.EtatFinancierOhada;
import com.ecomptaia.accounting.service.financial.EtatFinancierOhadaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.UUID;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.context.annotation.Import;
import com.ecomptaia.accounting.config.NoSecurityTestConfig;

@WebMvcTest(EtatFinancierOhadaController.class)
@Import(NoSecurityTestConfig.class)
class EtatFinancierOhadaControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EtatFinancierOhadaService etatFinancierOhadaService;
    @Autowired
    private ObjectMapper objectMapper;

    private Entreprise entrepriseNormal;
    private Entreprise entrepriseMinimal;

    @BeforeEach
    void setUp() {
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
    void testGenererEtatsNormal() throws Exception {
        EtatFinancierOhada etat = new EtatFinancierOhada();
        etat.setEntreprise(entrepriseNormal);
        etat.setTypeSystemeOhada(TypeSystemeOhada.NORMAL);
        etat.setExercice("2025");
        when(etatFinancierOhadaService.genererEtatsPourEntreprise(any(), any())).thenReturn(etat);
        mockMvc.perform(post("/api/etats-financiers-ohada/generer?exercice=2025")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(entrepriseNormal)))
                .andExpect(status().isOk());
    }

    @Test
    void testGenererEtatsMinimal() throws Exception {
        EtatFinancierOhada etat = new EtatFinancierOhada();
        etat.setEntreprise(entrepriseMinimal);
        etat.setTypeSystemeOhada(TypeSystemeOhada.MINIMAL);
        etat.setExercice("2025");
        when(etatFinancierOhadaService.genererEtatsPourEntreprise(any(), any())).thenReturn(etat);
        mockMvc.perform(post("/api/etats-financiers-ohada/generer?exercice=2025")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(entrepriseMinimal)))
                .andExpect(status().isOk());
    }
}
