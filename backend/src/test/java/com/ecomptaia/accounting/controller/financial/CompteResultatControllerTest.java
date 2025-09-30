package com.ecomptaia.accounting.controller.financial;

import com.ecomptaia.accounting.entity.financial.CompteResultat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.ecomptaia.accounting.service.financial.CompteResultatService;
import java.time.LocalDate;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ecomptaia.accounting.config.NoSecurityTestConfig;
import org.springframework.context.annotation.Import;

@WebMvcTest(CompteResultatController.class)
@Import(NoSecurityTestConfig.class)
public class CompteResultatControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CompteResultatService compteResultatService;

    @Test
    void testGetCompteResultat() throws Exception {
        when(compteResultatService.genererCompteResultat(LocalDate.parse("2025-01-01"), LocalDate.parse("2025-12-31"))).thenReturn(new CompteResultat());
        mockMvc.perform(get("/api/financial/compte-resultat")
                .param("dateDebut", "2025-01-01")
                .param("dateFin", "2025-12-31"))
                .andExpect(status().isOk());
    }
}
