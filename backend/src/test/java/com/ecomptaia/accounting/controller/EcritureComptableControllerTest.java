package com.ecomptaia.accounting.controller;

import com.ecomptaia.accounting.entity.EcritureComptable;
import com.ecomptaia.accounting.service.EcritureComptableService;
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

@WebMvcTest(EcritureComptableController.class)
@Import(NoSecurityTestConfig.class)
public class EcritureComptableControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EcritureComptableService ecritureComptableService;

    @Test
    void testGetByIdNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.when(ecritureComptableService.obtenirEcritureParId(id)).thenThrow(new RuntimeException());
        mockMvc.perform(get("/api/ecritures/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateEcriture() throws Exception {
        EcritureComptable ecriture = new EcritureComptable();
        ecriture.setNumero("EC-001");
        Mockito.when(ecritureComptableService.creerEcriture(Mockito.any())).thenReturn(ecriture);
        mockMvc.perform(post("/api/ecritures")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"numero\":\"EC-001\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numero").value("EC-001"));
    }

    @Test
    void testDeleteEcritureNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.doThrow(new RuntimeException()).when(ecritureComptableService).supprimerEcriture(id);
        mockMvc.perform(delete("/api/ecritures/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void testValiderEcritureBadRequest() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.when(ecritureComptableService.validerEcriture(Mockito.eq(id), Mockito.anyString())).thenThrow(new RuntimeException());
        mockMvc.perform(post("/api/ecritures/" + id + "/valider?validePar=testeur"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetByPeriode() throws Exception {
        Mockito.when(ecritureComptableService.obtenirEcrituresParPeriode(Mockito.any(), Mockito.any())).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/ecritures/periode?debut=2025-01-01&fin=2025-12-31"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
