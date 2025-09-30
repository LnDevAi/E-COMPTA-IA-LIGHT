package com.ecomptaia.accounting.controller;

import com.ecomptaia.accounting.entity.CompteComptable;
import com.ecomptaia.accounting.service.CompteComptableService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import com.ecomptaia.accounting.config.NoSecurityTestConfig;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CompteComptableController.class)
@Import(NoSecurityTestConfig.class)
public class CompteComptableControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompteComptableService compteComptableService;

    @Test
    void testGetAll() throws Exception {
        Mockito.when(compteComptableService.obtenirTousLesComptes()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/comptes"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void testCreateCompte() throws Exception {
        CompteComptable compte = new CompteComptable();
        compte.setNumero("101");
        Mockito.when(compteComptableService.creerCompte(Mockito.any())).thenReturn(compte);
        mockMvc.perform(post("/api/comptes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"numero\":\"101\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numero").value("101"));
    }

    @Test
    void testGetByNumeroNotFound() throws Exception {
        Mockito.when(compteComptableService.obtenirCompteParNumero("999")).thenThrow(new RuntimeException());
        mockMvc.perform(get("/api/comptes/numero/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateCompteNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.when(compteComptableService.modifierCompte(Mockito.eq(id), Mockito.any())).thenThrow(new RuntimeException());
        mockMvc.perform(put("/api/comptes/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"numero\":\"101\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeactivateCompteNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.doThrow(new RuntimeException()).when(compteComptableService).desactiverCompte(id);
        mockMvc.perform(delete("/api/comptes/" + id))
                .andExpect(status().isNotFound());
    }
}
