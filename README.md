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

### 📊 Analyse Complète
- **🔍 [Analyse Issues et Déploiements](ANALYSE_ISSUES_ET_DEPLOIEMENTS.md)** - ⭐ **NOUVEAU** - Analyse complète des issues ouvertes et état des déploiements

### Guides de Déploiement par Plateforme

- **🌐 [Render.com](RENDER_TROUBLESHOOTING.md)** - Déploiement Blueprint automatique (recommandé pour simplicité)
- **🚂 [Railway.app](RAILWAY_DEPLOYMENT.md)** - Déploiement avec nixpacks (recommandé pour démarrage rapide)
- **🌊 [DigitalOcean App Platform](DIGITALOCEAN_DEPLOYMENT.md)** - ⭐ **NOUVEAU** - Déploiement avec monitoring intégré
- **📋 [Guide Multi-Plateformes](DEPLOYMENT_GUIDE.md)** - Guide général pour toutes les plateformes
- **📘 [Guide Complet (Français)](GUIDE_DEPLOIEMENT_CORRECTIONS.md)** - Guide détaillé avec toutes les corrections

### CI/CD et Validation

- **✅ [Workflow Testing Guide](WORKFLOW_TESTING_GUIDE.md)** - Guide de test des workflows GitHub Actions
- **📊 [Workflow Validation Results](WORKFLOW_VALIDATION_RESULTS.md)** - Résultats des tests de validation
- **📖 [Workflows Documentation](WORKFLOWS_README.md)** - Documentation complète des workflows
- **🔍 [Verification Guide](VERIFICATION_WORKFLOWS.md)** - Guide de vérification des workflows

**Status**: ✅ Tous les workflows validés et fonctionnels (7/7)

### Déploiement Rapide

**Render.com** (⭐ Plus simple - Blueprint automatique) :
1. Dashboard Render → New Blueprint
2. Connecter le repository GitHub
3. Render lit `render.yaml` et déploie tout automatiquement
4. JWT_SECRET généré automatiquement

**Railway.app** (⚡ Plus rapide - Démarrage instantané) :
1. Connecter le dépôt à Railway
2. Définir `JWT_SECRET` dans les variables d'environnement
3. Deploy automatique avec `nixpacks.toml`
4. Frontend intégré dans backend

**DigitalOcean App Platform** (🎯 Plus complet - Monitoring intégré) :
1. Dashboard DO → Create App
2. Connecter le repository GitHub
3. DigitalOcean lit `.do/app.yaml`
4. Générer JWT_SECRET manuellement
5. Monitoring et métriques inclus

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
- POST /api/etats-financiers-audcif/generer?exercice=YYYY

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
- Table `entreprises` : id, nom, pays, systeme_comptable_id, type_systeme_audcif (ancien: type_systeme_ohada)
- Table `systemes_comptables` : id, code, libelle, description

---

## Déploiement Railway

**⚠️ Le guide de déploiement a été mis à jour avec l'intégration frontend.**

Voir le guide complet : [RAILWAY_DEPLOYMENT.md](RAILWAY_DEPLOYMENT.md)

**Configuration minimale** :
1. Créez un projet Railway
2. Connectez ce dépôt GitHub
3. Définissez `JWT_SECRET` dans les variables d'environnement
4. Railway détecte automatiquement `nixpacks.toml` et déploie
5. **Le frontend et backend sont déployés ensemble** - accédez à votre URL Railway pour voir l'application complète

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
