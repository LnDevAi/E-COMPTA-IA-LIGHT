package com.ecomptaia.accounting.service.financial.impl;

import com.ecomptaia.accounting.entity.financial.NoteAnnexe;
import com.ecomptaia.accounting.service.financial.NoteAnnexeService;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;

import java.util.ArrayList;

@Service
public class NoteAnnexeServiceImpl implements NoteAnnexeService {
    @Override
    public List<NoteAnnexe> genererNotesAnnexes() {
           List<NoteAnnexe> notes = new ArrayList<>();
           NoteAnnexe note = new NoteAnnexe();
           note.setTitre("Note sur les méthodes comptables");
           note.setContenu("Les états financiers sont établis conformément au SYSCOHADA révisé.");
           notes.add(note);
           return notes;
    }
}
