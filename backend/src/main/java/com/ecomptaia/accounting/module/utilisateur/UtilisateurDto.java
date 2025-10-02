package com.ecomptaia.accounting.module.utilisateur;

public class UtilisateurDto {
    private String nom;
    private String email;
    private String password;
    private String abonnement;
    private String[] modules;
    private String role;

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getAbonnement() { return abonnement; }
    public void setAbonnement(String abonnement) { this.abonnement = abonnement; }
    public String[] getModules() { return modules; }
    public void setModules(String[] modules) { this.modules = modules; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
