package com.ecomptaia.accounting.module.sycebnl;

import com.ecomptaia.accounting.module.sycebnl.entity.SycebnlOrganization;
import com.ecomptaia.accounting.module.sycebnl.entity.PieceJustificativeSycebnl;
import com.ecomptaia.accounting.module.sycebnl.repository.SycebnlOrganizationRepository;
import com.ecomptaia.accounting.module.sycebnl.repository.PieceJustificativeSycebnlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.scheduling.annotation.Async;
import java.util.List;
import com.ecomptaia.accounting.module.sycebnl.SycebnlOrganizationDto;
import com.ecomptaia.accounting.module.sycebnl.ValidationDto;
import com.ecomptaia.accounting.storage.FileStorageService;
import com.ecomptaia.accounting.ocr.OcrService;
import com.ecomptaia.accounting.ai.ImputationAiService;
import com.ecomptaia.accounting.repository.EcritureComptableRepository;
import com.ecomptaia.accounting.repository.CompteComptableRepository;
import com.ecomptaia.accounting.repository.JournalRepository;
import com.ecomptaia.accounting.entity.EcritureComptable;
import com.ecomptaia.accounting.entity.LigneEcriture;
import com.ecomptaia.accounting.storage.validators.UploadValidator;
import com.ecomptaia.accounting.storage.validators.AntivirusService;
import com.ecomptaia.accounting.security.SignedUrlService;

@Service
public class SycebnlService {
    @Autowired
    private SycebnlOrganizationRepository organizationRepository;
    @Autowired
    private PieceJustificativeSycebnlRepository pieceRepository;
    @Autowired
    private FileStorageService storage;
    @Autowired
    private OcrService ocrService;
    @Autowired
    private ImputationAiService aiService;
    @Autowired
    private EcritureComptableRepository ecritureRepository;
    @Autowired
    private CompteComptableRepository compteRepository;
    @Autowired
    private JournalRepository journalRepository;
    @Autowired
    private UploadValidator uploadValidator;
    @Autowired
    private AntivirusService antivirusService;
    @Autowired
    private SignedUrlService signedUrlService;
    @Autowired
    private JournalSelectionService journalSelectionService;

    // Mapping DTO -> Entity
    private SycebnlOrganization toEntity(SycebnlOrganizationDto dto) {
        SycebnlOrganization entity = new SycebnlOrganization();
        entity.setName(dto.name);
        entity.setLegalForm(dto.legalForm);
        entity.setRegistrationNumber(dto.registrationNumber);
        entity.setTaxIdentificationNumber(dto.taxIdentificationNumber);
        entity.setOrganizationType(dto.organizationType);
        entity.setFiscalYearStart(dto.fiscalYearStart);
        entity.setFiscalYearEnd(dto.fiscalYearEnd);
        entity.setBaseCurrency(dto.baseCurrency);
        entity.setReportingCurrency(dto.reportingCurrency);
        entity.setAnnualRevenue(dto.annualRevenue);
        entity.setEmployeeCount(dto.employeeCount);
        entity.setTotalAssets(dto.totalAssets);
        entity.setLegalAddress(dto.legalAddress);
        entity.setHeadquartersAddress(dto.headquartersAddress);
        entity.setPhoneNumber(dto.phoneNumber);
        entity.setEmail(dto.email);
        entity.setWebsite(dto.website);
        entity.setMissionStatement(dto.missionStatement);
        entity.setProgramAreas(dto.programAreas);
        entity.setGeographicScope(dto.geographicScope);
        entity.setBeneficiaryCount(dto.beneficiaryCount);
        entity.setVolunteerCount(dto.volunteerCount);
        return entity;
    }
    private SycebnlOrganizationDto toDto(SycebnlOrganization entity) {
        SycebnlOrganizationDto dto = new SycebnlOrganizationDto();
        dto.id = entity.getId();
        dto.name = entity.getName();
        dto.legalForm = entity.getLegalForm();
        dto.registrationNumber = entity.getRegistrationNumber();
        dto.taxIdentificationNumber = entity.getTaxIdentificationNumber();
        dto.organizationType = entity.getOrganizationType();
        dto.fiscalYearStart = entity.getFiscalYearStart();
        dto.fiscalYearEnd = entity.getFiscalYearEnd();
        dto.baseCurrency = entity.getBaseCurrency();
        dto.reportingCurrency = entity.getReportingCurrency();
        dto.annualRevenue = entity.getAnnualRevenue();
        dto.employeeCount = entity.getEmployeeCount();
        dto.totalAssets = entity.getTotalAssets();
        dto.legalAddress = entity.getLegalAddress();
        dto.headquartersAddress = entity.getHeadquartersAddress();
        dto.phoneNumber = entity.getPhoneNumber();
        dto.email = entity.getEmail();
        dto.website = entity.getWebsite();
        dto.missionStatement = entity.getMissionStatement();
        dto.programAreas = entity.getProgramAreas();
        dto.geographicScope = entity.getGeographicScope();
        dto.beneficiaryCount = entity.getBeneficiaryCount();
        dto.volunteerCount = entity.getVolunteerCount();
        return dto;
    }

    // CRUD Organisation
    public SycebnlOrganizationDto createOrganization(SycebnlOrganizationDto dto) {
        var entity = toEntity(dto);
        organizationRepository.save(entity);
        return toDto(entity);
    }
    public SycebnlOrganizationDto getOrganization(Long id) {
        var entity = organizationRepository.findById(id).orElse(null);
        return entity != null ? toDto(entity) : null;
    }
    public List<SycebnlOrganizationDto> listOrganizations() {
        return organizationRepository.findAll().stream().map(this::toDto).toList();
    }

    // Workflow GED + IA
    public Object uploadPieceJustificative(MultipartFile file, String libellePJ, String datePiece, String typePJ, Long entrepriseId, Long utilisateurId) {
        uploadValidator.validate(file);
        byte[] bytes;
        try { bytes = file.getBytes(); } catch (java.io.IOException e1) { throw new RuntimeException(e1); }
        antivirusService.scan(bytes);
        PieceJustificativeSycebnl pj = new PieceJustificativeSycebnl();
        pj.setLibellePJ(libellePJ);
        pj.setDatePiece(java.time.LocalDate.parse(datePiece));
        pj.setTypePJ(typePJ);
        pj.setEntrepriseId(entrepriseId);
        pj.setUtilisateurId(utilisateurId);
        // Idempotency: reject duplicates by content hash
        if (pieceRepository.existsBySha256(pj.getSha256())) {
            throw new RuntimeException("Duplicate document detected");
        }
        String path = storage.store(file, "pieces");
        pj.setFilePath(path);
        pj.setContentType(file.getContentType());
        pj.setSize(file.getSize());
        pj.setSha256(java.util.HexFormat.of().formatHex(java.security.MessageDigest.getInstance("SHA-256").digest(bytes)));
        pj.setStatus("UPLOADED");
        pieceRepository.save(pj);
        return pj;
    }

    public Object analyseOCR(Long id) {
        var pj = pieceRepository.findById(id).orElse(null);
        if (pj == null) return null;
        byte[] content = storage.read(pj.getFilePath());
        pj.setOcrResult(ocrService.extractText(content, pj.getFilePath()));
        pj.setStatus("OCR_DONE");
        pieceRepository.save(pj);
        return pj;
    }

    @Async
    public Object analyseIA(Long id) {
        var pj = pieceRepository.findById(id).orElse(null);
        if (pj == null) return null;
        var lines = aiService.proposeLines(pj.getOcrResult() != null ? pj.getOcrResult() : "");
        pj.setIaResult("Propositions: " + lines.size());
        pj.setStatus("IA_DONE");
        pieceRepository.save(pj);
        return pj;
    }

    public Object genererPropositions(Long id) {
        var pj = pieceRepository.findById(id).orElse(null);
        if (pj == null) return null;
        pj.setStatus("PROPOSITION_GENERATED");
        pieceRepository.save(pj);
        return "Propositions générées";
    }

    public Object validerProposition(Long id, ValidationDto validation) {
        var pj = pieceRepository.findById(id).orElse(null);
        if (pj == null) return null;
        pj.setStatus("VALIDATED");
        pieceRepository.save(pj);
        return "Proposition validée par " + validation.validateurId;
    }

    public Object genererEcriture(Long id) {
        var pj = pieceRepository.findById(id).orElse(null);
        if (pj == null) return null;
        var lines = aiService.proposeLines(pj.getOcrResult() != null ? pj.getOcrResult() : "");
        if (lines.isEmpty()) return "Aucune proposition";
        EcritureComptable e = new EcritureComptable();
        e.setLibelle("Pièce " + pj.getId() + " - " + pj.getLibellePJ());
        e.setDateEcriture(java.time.LocalDate.now());
        journalSelectionService.select(pj.getLibellePJ(), pj.getTypePJ(), pj.getOcrResult()).ifPresent(e::setJournal);
        for (var pl : lines) {
            var optCompte = compteRepository.findByNumero(pl.compteNumero);
            if (optCompte.isEmpty()) continue;
            LigneEcriture l = new LigneEcriture();
            l.setEcriture(e);
            l.setCompte(optCompte.get());
            l.setLibelle(pl.libelle);
            l.setMontantDebit(java.math.BigDecimal.valueOf(pl.debit));
            l.setMontantCredit(java.math.BigDecimal.valueOf(pl.credit));
            e.getLignes().add(l);
        }
        e = ecritureRepository.save(e);
        pj.setStatus("ECRITURE_GENERATED");
        pieceRepository.save(pj);
        return e;
    }

    public Object getEtatsFinanciers(Long organisationId) {
        return "États financiers SN/SMT pour organisation " + organisationId;
    }

    public Object genererNotesAnnexes(Long id) {
        return "Notes annexes générées pour état financier " + id;
    }

    // Listing & download helpers
    public java.util.List<PieceJustificativeSycebnl> listPieces() {
        return pieceRepository.findAll();
    }

    public byte[] downloadPiece(Long id, String token) {
        var pj = pieceRepository.findById(id).orElseThrow();
        if (token != null && !signedUrlService.verify(token, String.valueOf(id))) {
            throw new RuntimeException("Invalid or expired token");
        }
        return storage.read(pj.getFilePath());
    }
}
