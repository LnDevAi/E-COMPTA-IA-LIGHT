

package com.ecomptaia.accounting.entity.financial;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class LigneBilan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // The following fields are already present
    private String intitule;
    private double montant;
    private String type; // Actif ou Passif

    // Getters et setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getIntitule() { return intitule; }
    public void setIntitule(String intitule) { this.intitule = intitule; }
    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
