package com.ecomptaia.accounting.module.sycebnl;

import com.ecomptaia.accounting.module.sycebnl.entity.SycebnlOrganization;
import com.ecomptaia.accounting.module.sycebnl.entity.PieceJustificativeSycebnl;
import com.ecomptaia.accounting.module.sycebnl.repository.SycebnlOrganizationRepository;
import com.ecomptaia.accounting.module.sycebnl.repository.PieceJustificativeSycebnlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import com.ecomptaia.accounting.module.sycebnl.SycebnlOrganizationDto;
import com.ecomptaia.accounting.module.sycebnl.ValidationDto;

@Service
public class SycebnlService {
    @Autowired
    private SycebnlOrganizationRepository organizationRepository;
    @Autowired
    private PieceJustificativeSycebnlRepository pieceRepository;

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
        // Simule l'enregistrement du fichier et la création de la pièce
        PieceJustificativeSycebnl pj = new PieceJustificativeSycebnl();
        pj.setLibellePJ(libellePJ);
        pj.setDatePiece(java.time.LocalDate.parse(datePiece));
        pj.setTypePJ(typePJ);
        pj.setEntrepriseId(entrepriseId);
        pj.setUtilisateurId(utilisateurId);
        pj.setFilePath("/files/" + file.getOriginalFilename());
        pj.setStatus("UPLOADED");
        pieceRepository.save(pj);
        return pj;
    }

    public Object analyseOCR(Long id) {
        var pj = pieceRepository.findById(id).orElse(null);
        if (pj == null) return null;
        // Simule l'analyse OCR
        pj.setOcrResult("Texte extrait (OCR)");
        pj.setStatus("OCR_DONE");
        pieceRepository.save(pj);
        return pj;
    }

    public Object analyseIA(Long id) {
        var pj = pieceRepository.findById(id).orElse(null);
        if (pj == null) return null;
        // Simule l'analyse IA
        pj.setIaResult("Proposition IA : écriture générée");
        pj.setStatus("IA_DONE");
        pieceRepository.save(pj);
        return pj;
    }

    public Object genererPropositions(Long id) {
        var pj = pieceRepository.findById(id).orElse(null);
        if (pj == null) return null;
        // Simule la génération de propositions d'écritures
        pj.setStatus("PROPOSITION_GENERATED");
        pieceRepository.save(pj);
        return "Propositions générées";
    }

    public Object validerProposition(Long id, ValidationDto validation) {
        var pj = pieceRepository.findById(id).orElse(null);
        if (pj == null) return null;
        // Simule la validation
        pj.setStatus("VALIDATED");
        pieceRepository.save(pj);
        return "Proposition validée par " + validation.validateurId;
    }

    public Object genererEcriture(Long id) {
        var pj = pieceRepository.findById(id).orElse(null);
        if (pj == null) return null;
        // Simule la génération d'écriture comptable
        pj.setStatus("ECRITURE_GENERATED");
        pieceRepository.save(pj);
        return "Écriture générée";
    }

    public Object getEtatsFinanciers(Long organisationId) {
        // Simule la génération d'états financiers SN/SMT
        return "États financiers SN/SMT pour organisation " + organisationId;
    }

    public Object genererNotesAnnexes(Long id) {
        // Simule la génération de notes annexes
        return "Notes annexes générées pour état financier " + id;
    }
}
