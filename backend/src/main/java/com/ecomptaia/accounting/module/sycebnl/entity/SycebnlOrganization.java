package com.ecomptaia.accounting.module.sycebnl.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "sycebnl_organization")
public class SycebnlOrganization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String legalForm;
    private String registrationNumber;
    private String taxIdentificationNumber;
    private String organizationType;
    private String fiscalYearStart;
    private String fiscalYearEnd;
    private String baseCurrency;
    private String reportingCurrency;
    private String annualRevenue;
    private String employeeCount;
    private String totalAssets;
    private String legalAddress;
    private String headquartersAddress;
    private String phoneNumber;
    private String email;
    private String website;
    private String missionStatement;
    private String programAreas;
    private String geographicScope;
    private String beneficiaryCount;
    private String volunteerCount;
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLegalForm() { return legalForm; }
    public void setLegalForm(String legalForm) { this.legalForm = legalForm; }
    public String getRegistrationNumber() { return registrationNumber; }
    public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }
    public String getTaxIdentificationNumber() { return taxIdentificationNumber; }
    public void setTaxIdentificationNumber(String taxIdentificationNumber) { this.taxIdentificationNumber = taxIdentificationNumber; }
    public String getOrganizationType() { return organizationType; }
    public void setOrganizationType(String organizationType) { this.organizationType = organizationType; }
    public String getFiscalYearStart() { return fiscalYearStart; }
    public void setFiscalYearStart(String fiscalYearStart) { this.fiscalYearStart = fiscalYearStart; }
    public String getFiscalYearEnd() { return fiscalYearEnd; }
    public void setFiscalYearEnd(String fiscalYearEnd) { this.fiscalYearEnd = fiscalYearEnd; }
    public String getBaseCurrency() { return baseCurrency; }
    public void setBaseCurrency(String baseCurrency) { this.baseCurrency = baseCurrency; }
    public String getReportingCurrency() { return reportingCurrency; }
    public void setReportingCurrency(String reportingCurrency) { this.reportingCurrency = reportingCurrency; }
    public String getAnnualRevenue() { return annualRevenue; }
    public void setAnnualRevenue(String annualRevenue) { this.annualRevenue = annualRevenue; }
    public String getEmployeeCount() { return employeeCount; }
    public void setEmployeeCount(String employeeCount) { this.employeeCount = employeeCount; }
    public String getTotalAssets() { return totalAssets; }
    public void setTotalAssets(String totalAssets) { this.totalAssets = totalAssets; }
    public String getLegalAddress() { return legalAddress; }
    public void setLegalAddress(String legalAddress) { this.legalAddress = legalAddress; }
    public String getHeadquartersAddress() { return headquartersAddress; }
    public void setHeadquartersAddress(String headquartersAddress) { this.headquartersAddress = headquartersAddress; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }
    public String getMissionStatement() { return missionStatement; }
    public void setMissionStatement(String missionStatement) { this.missionStatement = missionStatement; }
    public String getProgramAreas() { return programAreas; }
    public void setProgramAreas(String programAreas) { this.programAreas = programAreas; }
    public String getGeographicScope() { return geographicScope; }
    public void setGeographicScope(String geographicScope) { this.geographicScope = geographicScope; }
    public String getBeneficiaryCount() { return beneficiaryCount; }
    public void setBeneficiaryCount(String beneficiaryCount) { this.beneficiaryCount = beneficiaryCount; }
    public String getVolunteerCount() { return volunteerCount; }
    public void setVolunteerCount(String volunteerCount) { this.volunteerCount = volunteerCount; }
}
