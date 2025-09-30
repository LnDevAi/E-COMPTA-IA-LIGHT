# E-COMPTA-IA-LIGHT

Plateforme comptable légère avec Intelligence Artificielle et intégrations tierces.

## Modules Implémentés

### Module Authentification & Sécurité
- Inscription (register)
- Connexion (login)
- JWT tokens sécurisés
- Rôles utilisateurs (ADMIN, ACCOUNTANT, USER, VIEWER)
- Base de données H2 en mémoire

## Technologies

- Backend : Spring Boot 3.2.5, Java 17
- Base de données : H2 (dev)
- Sécurité : Spring Security + JWT
- Build : Maven

## Démarrage Rapide

### Prérequis
- Java 17+
- Maven 3.9+

### Installation
```bash
git clone https://github.com/LnDevAi/E-COMPTA-IA-LIGHT.git
cd E-COMPTA-IA-LIGHT/backend
mvn spring-boot:run
