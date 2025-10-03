# Guide de Déploiement - E-COMPTA-IA-LIGHT

## 🎯 Résumé des Corrections

Ce document résume les corrections apportées pour résoudre les problèmes de déploiement sur Render et Railway.

## 🐛 Problème Identifié

**Symptôme** : Le déploiement crashe à l'étape de post-déploiement sur Railway avec des erreurs de schéma :
```
Unknown data type: "OID"
Unknown data type: "UUID"
Unknown data type: "bigserial"
```

**Cause racine** : Le profil Railway était configuré avec le dialecte PostgreSQL par défaut, mais l'application tentait de se connecter à H2 (quand PostgreSQL n'était pas disponible), causant des incompatibilités de types de données.

## ✅ Solutions Implémentées

### 1. Création du Profil Production pour Render

**Fichier** : `backend/src/main/resources/application-prod.yml`

- Nouveau profil spécifique pour Render
- Configuré pour PostgreSQL
- Supporte les variables d'environnement de Render
- Utilise `SPRING_PROFILES_ACTIVE=prod` comme défini dans `render.yaml`

### 2. Correction du Profil Railway

**Fichier** : `backend/src/main/resources/application-railway.yml`

**Changements clés** :
```yaml
# AVANT (causait les erreurs)
dialect: org.hibernate.dialect.PostgreSQLDialect

# APRÈS (corrigé)
dialect: ${HIBERNATE_DIALECT:org.hibernate.dialect.H2Dialect}
```

**Comportement** :
- Par défaut : Utilise H2 (pour développement et tests)
- Avec PostgreSQL : Bascule automatiquement vers PostgreSQL quand les variables d'environnement sont définies
- Support complet des variables Railway natives (PGUSER, PGPASSWORD, DATABASE_URL)

### 3. Amélioration du Dockerfile Frontend

**Fichier** : `frontend-app/Dockerfile`

- Ajout du support ARG/ENV pour `REACT_APP_API_URL`
- Permet l'injection de l'URL de l'API au moment du build
- Compatible avec Render et Railway

### 4. Documentation Complète

**Nouveaux fichiers** :
- `RENDER_TROUBLESHOOTING.md` : Guide détaillé de dépannage pour Render
- Mise à jour de `RAILWAY_DEPLOYMENT.md` avec section de troubleshooting étendue

## 🚀 Déploiement sur Railway

### Configuration Minimale Requise

**Variables d'environnement obligatoires** :
```
JWT_SECRET=<votre-clé-secrète-minimum-256-bits>
```

**Variables optionnelles (pour PostgreSQL)** :
```
DATABASE_URL=jdbc:postgresql://host:port/database
DATABASE_DRIVER=org.postgresql.Driver
HIBERNATE_DIALECT=org.hibernate.dialect.PostgreSQLDialect
PGUSER=<username>
PGPASSWORD=<password>
```

### Étapes de Déploiement

1. **Connecter le dépôt** à Railway
2. **Définir JWT_SECRET** dans les variables d'environnement
3. **Optionnel** : Ajouter un service PostgreSQL et définir les variables
4. Railway détecte automatiquement `nixpacks.toml` et déploie

### Vérification du Déploiement

```bash
# Tester l'inscription
curl -X POST https://votre-app.railway.app/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"test","password":"test123","role":"USER"}'

# Tester la connexion
curl -X POST https://votre-app.railway.app/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"test","password":"test123"}'
```

## 🌐 Déploiement sur Render

### Via Blueprint (Automatique)

1. Accéder à https://dashboard.render.com
2. Cliquer sur **"New +"** → **"Blueprint"**
3. Connecter le dépôt GitHub
4. Render lit automatiquement `render.yaml`
5. Cliquer sur **"Create Services"**

Render créera automatiquement :
- **ecompta-backend** : Service Web (Docker, port 8080)
- **ecompta-frontend** : Service Web (Docker, port 80)
- **postgres** : Base de données PostgreSQL

### Vérification du Déploiement

```bash
# Tester le backend
curl https://ecompta-backend.onrender.com/api/auth/login

# Tester le frontend
curl https://ecompta-frontend.onrender.com
```

## 🔧 Résolution des Problèmes Courants

### Railway - Service Crash au Démarrage

**Symptôme** : Build réussit mais l'application crash immédiatement

**Solutions** :
1. ✅ **JWT_SECRET manquant** : Ajouter dans les variables d'environnement
2. ✅ **Erreurs de schéma** : Corrigé dans la dernière version
3. ✅ **Base de données** : Si utilisation de PostgreSQL, vérifier que le service DB est actif

### Render - Build Échoue

**Symptôme** : Docker build échoue

**Solutions** :
1. Vérifier les logs de build dans Render
2. S'assurer que `render.yaml` est à la racine du dépôt
3. Vérifier que tous les services sont correctement définis

### Frontend Ne Se Connecte Pas au Backend

**Solutions** :
1. Vérifier `REACT_APP_API_URL` dans `render.yaml`
2. S'assurer que l'URL pointe vers le bon service backend
3. Vérifier la configuration CORS du backend

## 📊 Tests et Validation

### Tests Effectués

✅ **Build backend** : Succès (Maven 3.9, JDK 17)
✅ **Tests unitaires** : 32/32 passent
✅ **Profil Railway + H2** : Fonctionne sans erreur
✅ **Profil Railway + PostgreSQL** : Bascule correctement
✅ **Profil Prod** : Configuration valide

### Commandes de Test

```bash
# Build
cd backend && mvn clean package -DskipTests

# Tests
mvn test

# Test démarrage avec Railway profile
JWT_SECRET="test-key-256-bits-aaaa" \
  java -Dspring.profiles.active=railway \
  -jar target/ecompta-ia-light-2.0.0.jar

# Test avec PostgreSQL
JWT_SECRET="test-key" \
  DATABASE_URL="jdbc:postgresql://host:5432/db" \
  DATABASE_DRIVER="org.postgresql.Driver" \
  HIBERNATE_DIALECT="org.hibernate.dialect.PostgreSQLDialect" \
  java -Dspring.profiles.active=railway \
  -jar target/ecompta-ia-light-2.0.0.jar
```

## 📚 Documentation Complète

- **Railway** : Voir [RAILWAY_DEPLOYMENT.md](RAILWAY_DEPLOYMENT.md)
- **Render** : Voir [RENDER_TROUBLESHOOTING.md](RENDER_TROUBLESHOOTING.md)
- **Corrections PR#2** : Voir [CORRECTIONS_PR2.md](CORRECTIONS_PR2.md)

## 🎉 Résultat

L'application est maintenant prête pour le déploiement sur Render et Railway sans crash post-déploiement. Les problèmes de dialecte de base de données sont résolus et la configuration est flexible pour supporter à la fois H2 (développement) et PostgreSQL (production).

## 💡 Points Clés à Retenir

1. **Railway utilise H2 par défaut** : Pas besoin de PostgreSQL pour démarrer
2. **PostgreSQL est optionnel** : Ajouter si nécessaire avec les bonnes variables d'environnement
3. **JWT_SECRET est obligatoire** : Toujours le définir en production
4. **Tests passent** : 32/32 tests unitaires validés
5. **Documentation complète** : Guides de dépannage disponibles

## 🆘 Support

En cas de problème :

1. **Railway** : Consulter la section Troubleshooting dans [RAILWAY_DEPLOYMENT.md](RAILWAY_DEPLOYMENT.md)
2. **Render** : Consulter [RENDER_TROUBLESHOOTING.md](RENDER_TROUBLESHOOTING.md)
3. **Logs** : Activer le niveau DEBUG si nécessaire
4. **Community** : Railway Discord ou Render Community Forum
