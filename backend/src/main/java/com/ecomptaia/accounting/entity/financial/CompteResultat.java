package com.ecomptaia.accounting.entity.financial;

import java.util.List;

public class CompteResultat {
    private List<LigneResultat> produits;
    private List<LigneResultat> charges;
    private double totalProduits;
    private double totalCharges;
    private double resultatNet;

    // Getters et setters
    public List<LigneResultat> getProduits() { return produits; }
    public void setProduits(List<LigneResultat> produits) { this.produits = produits; }
    public List<LigneResultat> getCharges() { return charges; }
    public void setCharges(List<LigneResultat> charges) { this.charges = charges; }
    public double getTotalProduits() { return totalProduits; }
    public void setTotalProduits(double totalProduits) { this.totalProduits = totalProduits; }
    public double getTotalCharges() { return totalCharges; }
    public void setTotalCharges(double totalCharges) { this.totalCharges = totalCharges; }
    public double getResultatNet() { return resultatNet; }
    public void setResultatNet(double resultatNet) { this.resultatNet = resultatNet; }
}
