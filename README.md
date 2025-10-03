# E-COMPTA-IA-LIGHT

Plateforme comptable légère avec Intelligence Artificielle et intégrations tierces.

## Modules Implémentés

### Module Authentification & Sécurité
- Inscription (register)
- Connexion (login)
- JWT tokens sécurisés

- Backend : Spring Boot 3.2.5, Java 17
- Base de données : H2 (dev)
- Sécurité : Spring Security + JWT

### Prérequis
- Java 17+
- Maven 3.9+

## 🚀 Déploiement Cloud

### Guides de Déploiement Disponibles

- **📘 [Guide Complet de Déploiement (Français)](GUIDE_DEPLOIEMENT_CORRECTIONS.md)** - Guide détaillé avec toutes les corrections
- **🔧 [Railway Deployment Guide](RAILWAY_DEPLOYMENT.md)** - Déploiement sur Railway avec troubleshooting
- **🌐 [Render Troubleshooting](RENDER_TROUBLESHOOTING.md)** - Guide de dépannage pour Render
- **📋 [Corrections PR#2](CORRECTIONS_PR2.md)** - Historique des corrections

### Déploiement Rapide

**Railway** (recommandé pour démarrage rapide) :
1. Connecter le dépôt à Railway
2. Définir `JWT_SECRET` dans les variables d'environnement
3. Déployer automatiquement avec nixpacks

**Render** (recommandé pour production) :
1. Utiliser le Blueprint : `render.yaml`
2. Backend + Frontend + PostgreSQL déployés automatiquement
3. Variables d'environnement configurées automatiquement

### ✅ Corrections Post-Déploiement Appliquées

Le projet a été corrigé pour résoudre les problèmes de crash post-déploiement :
- ✅ Dialecte de base de données corrigé (H2 par défaut, PostgreSQL auto-détecté)
- ✅ Support complet de Railway et Render
- ✅ Documentation complète de troubleshooting
- ✅ 32 tests unitaires passent


git clone https://github.com/LnDevAi/E-COMPTA-IA-LIGHT.git
cd E-COMPTA-IA-LIGHT/backend
mvn spring-boot:run
git clone https://github.com/LnDevAi/E-COMPTA-IA-LIGHT.git
cd E-COMPTA-IA-LIGHT/backend
mvn spring-boot:run



## Endpoints API

### Authentification



- DELETE /api/comptes/{id}

- PUT /api/journaux/{id}

- POST /api/ecritures



- GET /api/rapports/bilan?date=YYYY-MM-DD

### États Financiers
- GET /api/etats-financiers/ohada?date=YYYY-MM-DD

---

## Exemples curl


### Inscription
```bash
curl -X POST http://localhost:8080/api/auth/register -H "Content-Type: application/json" -d '{"username":"admin","password":"admin123","role":"ADMIN"}'
```


### Connexion (récupérer le token)
```bash
curl -X POST http://localhost:8080/api/auth/login -H "Content-Type: application/json" -d '{"username":"admin","password":"admin123"}'
```


### Créer un compte
```bash
curl -X POST http://localhost:8080/api/comptes -H "Authorization: Bearer {token}" -H "Content-Type: application/json" -d '{"numero":"6000","libelle":"Achats","type":"ACTIF","nature":"DEBIT"}'
```


### Créer une écriture équilibrée
```bash
curl -X POST http://localhost:8080/api/ecritures -H "Authorization: Bearer {token}" -H "Content-Type: application/json" -d '{"libelle":"Achat marchandises","date":"2025-10-01","journalCode":"AC","lignes":[{"compte":"6000","debit":50000,"credit":0},{"compte":"401","debit":0,"credit":50000}]}'
```


### Valider une écriture
```bash
curl -X POST http://localhost:8080/api/ecritures/{id}/valider -H "Authorization: Bearer {token}"
```

### Générer la balance
```bash
curl -X GET "http://localhost:8080/api/rapports/balance?dateDebut=2025-01-01&dateFin=2025-12-31" -H "Authorization: Bearer {token}"
```

---

## Documentation interactive

-------------------+
Swagger UI : [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
-------------------+

---
-------------------+

-------------------+
## Architecture (ASCII Art)

-------------------+
```
-------------------+
+-------------------+

+-------------------+
		 |

+-------------------+
```

---

## Schéma de la base de données
- Table `users` : id, username, password, role
- Table `comptes_comptables` : id, numero, libelle, type, nature, actif, systeme_comptable_id
- Table `journaux` : id, code, libelle
- Table `ecritures_comptables` : id, libelle, date, journal_id
- Table `lignes_ecriture` : id, ecriture_id, compte_id, debit, credit
- Table `entreprises` : id, nom, pays, systeme_comptable_id, type_systeme_ohada
- Table `systemes_comptables` : id, code, libelle, description

---

## Déploiement Railway

**⚠️ Le guide de déploiement a été mis à jour avec les corrections post-déploiement.**

Voir le guide complet : [RAILWAY_DEPLOYMENT.md](RAILWAY_DEPLOYMENT.md)

**Configuration minimale** :
1. Créez un projet Railway
2. Connectez ce dépôt GitHub
3. Définissez `JWT_SECRET` dans les variables d'environnement
4. Railway détecte automatiquement `nixpacks.toml` et déploie

**PostgreSQL (optionnel)** :
- Ajoutez un service PostgreSQL dans Railway
- Les variables sont auto-injectées (PGUSER, PGPASSWORD, DATABASE_URL)
- L'application bascule automatiquement vers PostgreSQL

Pour plus de détails et troubleshooting : [RAILWAY_DEPLOYMENT.md](RAILWAY_DEPLOYMENT.md)

---

## Déploiement Render

**Via Blueprint automatique** (recommandé) :
1. Accédez à https://dashboard.render.com
2. Créez un nouveau Blueprint
3. Connectez ce dépôt GitHub
4. Render lit `render.yaml` et crée tous les services automatiquement

Pour troubleshooting : [RENDER_TROUBLESHOOTING.md](RENDER_TROUBLESHOOTING.md)

---

## Variables d'environnement

- `JWT_SECRET` : clé secrète pour les tokens JWT
- `JWT_EXPIRATION` : durée de validité du token (ex: 86400000)
- `SPRING_DATASOURCE_URL` : URL JDBC de la base (ex: jdbc:h2:mem:testdb)
- `SPRING_DATASOURCE_USERNAME` : utilisateur DB
- `SPRING_DATASOURCE_PASSWORD` : mot de passe DB
- `SPRING_PROFILES_ACTIVE` : profil Spring (ex: prod, dev)

---
