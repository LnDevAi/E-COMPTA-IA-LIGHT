package com.ecomptaia.accounting.module.ged;


import com.ecomptaia.accounting.entity.PieceComptableGed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class GedService {
    @Autowired
    private GedRepository gedRepository;

    public PieceComptableGed saveFile(MultipartFile file, String uploadedBy) throws IOException {
        PieceComptableGed piece = new PieceComptableGed();
        piece.setFilename(file.getName());
        piece.setOriginalFilename(file.getOriginalFilename());
        piece.setContentType(file.getContentType());
        piece.setSize(file.getSize());
        piece.setUploadDate(LocalDateTime.now());
        piece.setData(file.getBytes());
        piece.setUploadedBy(uploadedBy);
        return gedRepository.save(piece);
    }
}
