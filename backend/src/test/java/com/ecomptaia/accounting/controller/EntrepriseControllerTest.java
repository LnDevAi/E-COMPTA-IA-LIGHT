package com.ecomptaia.accounting.controller;

import com.ecomptaia.accounting.entity.Entreprise;
import com.ecomptaia.accounting.entity.SystemeComptable;
import com.ecomptaia.accounting.entity.TypeSystemeOhada;
import com.ecomptaia.accounting.repository.EntrepriseRepository;
import com.ecomptaia.accounting.repository.SystemeComptableRepository;
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

import com.ecomptaia.accounting.service.EntrepriseService;

@WebMvcTest(EntrepriseController.class)
@org.springframework.context.annotation.Import(com.ecomptaia.accounting.config.NoSecurityTestConfig.class)
class EntrepriseControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EntrepriseService entrepriseService;
    @MockBean
    private EntrepriseRepository entrepriseRepository;
    @MockBean
    private SystemeComptableRepository systemeComptableRepository;
    @Autowired
    private ObjectMapper objectMapper;

    private SystemeComptable systemeOhada;

    @BeforeEach
    void setUp() {
        systemeOhada = new SystemeComptable();
        systemeOhada.setId(1L);
        systemeOhada.setCode("OHADA");
        systemeOhada.setLibelle("OHADA");
    }

    @Test
    void testCreerEntrepriseAutomatique_OHADA_Normal() throws Exception {
        Entreprise ent = new Entreprise();
        ent.setId(UUID.randomUUID());
        ent.setNom("Test SA");
        ent.setPays("CI");
        ent.setSystemeComptable(systemeOhada);
        ent.setTypeSystemeOhada(TypeSystemeOhada.NORMAL);
        when(entrepriseService.creerEntrepriseAutomatique(any())).thenReturn(ent);
        mockMvc.perform(post("/api/entreprises/automatique")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(ent)))
                .andExpect(status().isOk());
    }

    @Test
    void testCreerEntrepriseAutomatique_OHADA_Minimal() throws Exception {
        Entreprise ent = new Entreprise();
        ent.setId(UUID.randomUUID());
        ent.setNom("Petite SARL");
        ent.setPays("CI");
        ent.setSystemeComptable(systemeOhada);
        ent.setTypeSystemeOhada(TypeSystemeOhada.MINIMAL);
        when(entrepriseService.creerEntrepriseAutomatique(any())).thenReturn(ent);
        mockMvc.perform(post("/api/entreprises/automatique")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(ent)))
                .andExpect(status().isOk());
    }
}
