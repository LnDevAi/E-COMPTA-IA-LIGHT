package com.ecomptaia.accounting.controller.financial;

import com.ecomptaia.accounting.entity.financial.Bilan;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.ecomptaia.accounting.service.financial.BilanService;
import java.time.LocalDate;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ecomptaia.accounting.config.NoSecurityTestConfig;
import org.springframework.context.annotation.Import;

@WebMvcTest(BilanController.class)
@Import(NoSecurityTestConfig.class)
public class BilanControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BilanService bilanService;

    @Test
    void testGetBilan() throws Exception {
        when(bilanService.genererBilan(LocalDate.parse("2025-01-01"), LocalDate.parse("2025-12-31"))).thenReturn(new Bilan());
        mockMvc.perform(get("/api/financial/bilan")
                .param("dateDebut", "2025-01-01")
                .param("dateFin", "2025-12-31"))
                .andExpect(status().isOk());
    }
}
