package com.ecomptaia.accounting.module.utilisateur;

public class AbonnementDto {
    private String email;
    private String abonnement;
    private String[] modules;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAbonnement() { return abonnement; }
    public void setAbonnement(String abonnement) { this.abonnement = abonnement; }
    public String[] getModules() { return modules; }
    public void setModules(String[] modules) { this.modules = modules; }
}
