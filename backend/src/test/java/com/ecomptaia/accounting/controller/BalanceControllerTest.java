package com.ecomptaia.accounting.controller;

import com.ecomptaia.accounting.service.CompteComptableService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import com.ecomptaia.accounting.config.NoSecurityTestConfig;

import java.time.LocalDate;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BalanceController.class)
@Import(NoSecurityTestConfig.class)
public class BalanceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompteComptableService compteComptableService;

    @Test
    void testGetBalance() throws Exception {
        Mockito.when(compteComptableService.calculerBalance(Mockito.any(), Mockito.any()))
                .thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/comptes/balance?debut=2025-01-01&fin=2025-12-31"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
