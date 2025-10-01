package com.ecomptaia.accounting.controller.financial;

import com.ecomptaia.accounting.entity.financial.NoteAnnexe;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.ecomptaia.accounting.service.financial.NoteAnnexeService;
import java.util.Collections;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ecomptaia.accounting.config.NoSecurityTestConfig;
import org.springframework.context.annotation.Import;

@WebMvcTest(NoteAnnexeController.class)
@Import(NoSecurityTestConfig.class)
public class NoteAnnexeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private NoteAnnexeService noteAnnexeService;

    @Test
    void testGetNotesAnnexes() throws Exception {
        when(noteAnnexeService.genererNotesAnnexes("NORMAL")).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/financial/notes-annexes?systeme=NORMAL"))
                .andExpect(status().isOk());
    }
}
