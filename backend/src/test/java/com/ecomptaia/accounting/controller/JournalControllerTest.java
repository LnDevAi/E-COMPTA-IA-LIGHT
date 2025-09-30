package com.ecomptaia.accounting.controller;

import com.ecomptaia.accounting.entity.Journal;
import com.ecomptaia.accounting.service.JournalService;
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

@WebMvcTest(JournalController.class)
@Import(NoSecurityTestConfig.class)
public class JournalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JournalService journalService;

    @Test
    void testGetAll() throws Exception {
        Mockito.when(journalService.obtenirTousLesJournaux()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/journaux"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void testCreateJournal() throws Exception {
        Journal journal = new Journal();
        journal.setCode("ACH");
        Mockito.when(journalService.creerJournal(Mockito.any())).thenReturn(journal);
        mockMvc.perform(post("/api/journaux")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"code\":\"ACH\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("ACH"));
    }

    @Test
    void testGetByCodeNotFound() throws Exception {
        Mockito.when(journalService.obtenirJournalParCode("XXX")).thenThrow(new RuntimeException());
        mockMvc.perform(get("/api/journaux/code/XXX"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateJournalNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.when(journalService.modifierJournal(Mockito.eq(id), Mockito.any())).thenThrow(new RuntimeException());
        mockMvc.perform(put("/api/journaux/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"code\":\"ACH\"}"))
                .andExpect(status().isNotFound());
    }
}
