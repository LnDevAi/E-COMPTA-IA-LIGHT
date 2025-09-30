package com.ecomptaia.accounting.controller;

import com.ecomptaia.accounting.entity.EcritureComptable;
import com.ecomptaia.accounting.service.CompteComptableService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.ecomptaia.accounting.config.NoSecurityTestConfig;

import java.time.LocalDate;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GrandLivreController.class)
@Import(NoSecurityTestConfig.class)
public class GrandLivreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompteComptableService compteComptableService;

    @Test
    void testGetGrandLivre() throws Exception {
        Mockito.when(compteComptableService.obtenirGrandLivre(Mockito.eq("101"), Mockito.any(), Mockito.any()))
                .thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/comptes/101/grand-livre?debut=2025-01-01&fin=2025-12-31"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void testGetGrandLivreCompteNotFound() throws Exception {
        Mockito.when(compteComptableService.obtenirGrandLivre(Mockito.eq("999"), Mockito.any(), Mockito.any()))
                .thenThrow(new RuntimeException());
        mockMvc.perform(get("/api/comptes/999/grand-livre?debut=2025-01-01&fin=2025-12-31"))
                .andExpect(status().isNotFound());
    }
}
