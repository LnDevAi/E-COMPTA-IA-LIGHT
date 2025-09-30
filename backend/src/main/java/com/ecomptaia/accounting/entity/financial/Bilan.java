package com.ecomptaia.accounting.entity.financial;

import java.util.List;

public class Bilan {
    private List<LigneBilan> actif;
    private List<LigneBilan> passif;
    private double totalActif;
    private double totalPassif;

    // Getters et setters
    public List<LigneBilan> getActif() { return actif; }
    public void setActif(List<LigneBilan> actif) { this.actif = actif; }
    public List<LigneBilan> getPassif() { return passif; }
    public void setPassif(List<LigneBilan> passif) { this.passif = passif; }
    public double getTotalActif() { return totalActif; }
    public void setTotalActif(double totalActif) { this.totalActif = totalActif; }
    public double getTotalPassif() { return totalPassif; }
    public void setTotalPassif(double totalPassif) { this.totalPassif = totalPassif; }
}
