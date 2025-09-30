
package com.ecomptaia.accounting.entity.financial;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class Bilan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany
    private List<LigneBilan> actif;
    @OneToMany
    private List<LigneBilan> passif;
    private double totalActif;
    private double totalPassif;

    // Getters et setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public List<LigneBilan> getActif() { return actif; }
    public void setActif(List<LigneBilan> actif) { this.actif = actif; }
    public List<LigneBilan> getPassif() { return passif; }
    public void setPassif(List<LigneBilan> passif) { this.passif = passif; }
    public double getTotalActif() { return totalActif; }
    public void setTotalActif(double totalActif) { this.totalActif = totalActif; }
    public double getTotalPassif() { return totalPassif; }
    public void setTotalPassif(double totalPassif) { this.totalPassif = totalPassif; }
}
