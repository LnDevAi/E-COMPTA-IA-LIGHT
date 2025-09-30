package com.ecomptaia.accounting.entity.financial;

public class LigneBilan {
    private String intitule;
    private double montant;
    private String type; // Actif ou Passif

    // Getters et setters
    public String getIntitule() { return intitule; }
    public void setIntitule(String intitule) { this.intitule = intitule; }
    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
