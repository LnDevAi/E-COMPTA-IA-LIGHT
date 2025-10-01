package com.ecomptaia.accounting.controller.financial;

import org.springframework.web.bind.annotation.RequestParam;

import com.ecomptaia.accounting.entity.financial.NoteAnnexe;
import com.ecomptaia.accounting.service.financial.NoteAnnexeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/financial/notes-annexes")
public class NoteAnnexeController {
    @Autowired
    private NoteAnnexeService noteAnnexeService;

    @GetMapping
    public List<NoteAnnexe> getNotesAnnexes(@RequestParam(required = false, defaultValue = "NORMAL") String systeme) {
        return noteAnnexeService.genererNotesAnnexes(systeme);
    }
}
