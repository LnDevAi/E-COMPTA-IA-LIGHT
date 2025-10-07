# Guide de Déploiement DigitalOcean App Platform

Ce guide explique comment déployer E-COMPTA-IA-LIGHT sur DigitalOcean App Platform.

## 🎯 Vue d'Ensemble

DigitalOcean App Platform est une solution PaaS (Platform as a Service) similaire à Render et Railway, avec déploiement automatisé via configuration Infrastructure as Code.

### Avantages DigitalOcean App Platform

- ✅ **Déploiement automatique** via `.do/app.yaml`
- ✅ **Build Docker natif** (supporte nos Dockerfiles)
- ✅ **Base de données PostgreSQL managée** incluse
- ✅ **SSL/TLS automatique** avec certificats gratuits
- ✅ **CI/CD intégré** avec GitHub
- ✅ **Scaling facile** via dashboard
- ✅ **Monitoring intégré** (métriques, logs, alertes)
- ✅ **Prix compétitifs** (à partir de $5/mois)

---

## 📋 Prérequis

1. **Compte DigitalOcean**
   - Créer un compte sur https://cloud.digitalocean.com
   - Ajouter une méthode de paiement
   - Activer App Platform (gratuit pendant 30 jours, puis à partir de $5/mois)

2. **Repository GitHub**
   - Accès au repository E-COMPTA-IA-LIGHT
   - Permissions pour connecter DigitalOcean à GitHub

---

## 🚀 Méthode 1: Déploiement Automatique (Recommandé)

### Utilisation de `.do/app.yaml`

Le fichier `.do/app.yaml` à la racine du projet permet un déploiement entièrement automatisé.

#### Étapes

1. **Accéder à DigitalOcean App Platform**
   ```
   https://cloud.digitalocean.com/apps
   ```

2. **Créer une nouvelle App**
   - Cliquer sur "Create App"
   - Sélectionner "GitHub" comme source
   - Autoriser DigitalOcean à accéder à votre compte GitHub (si première fois)

3. **Connecter le Repository**
   - Sélectionner le repository: `LnDevAi/E-COMPTA-IA-LIGHT`
   - Sélectionner la branche: `main`
   - Cocher "Autodeploy" pour déploiement automatique sur chaque push
   - Cliquer sur "Next"

4. **Configuration Automatique**
   - DigitalOcean détecte automatiquement `.do/app.yaml`
   - Message affiché: "App Spec detected"
   - Cliquer sur "Use App Spec"

5. **Configurer les Secrets**
   - Dans la section "Environment Variables"
   - Trouver la variable `JWT_SECRET` (marquée comme SECRET)
   - Cliquer sur "Edit" à côté de JWT_SECRET
   - Générer une clé sécurisée (minimum 256 bits):
     ```bash
     openssl rand -base64 32
     ```
   - Coller la valeur générée
   - Cliquer sur "Save"

6. **Réviser et Créer**
   - Vérifier les ressources qui seront créées:
     - ✅ Backend Service (Docker, $5/mois)
     - ✅ Frontend Service (Docker, $5/mois)
     - ✅ PostgreSQL Database (dev: $7/mois, prod: $15/mois)
   - Sélectionner la région (par défaut: New York - NYC)
   - Cliquer sur "Create Resources"

7. **Attendre le Déploiement**
   - DigitalOcean va:
     1. Cloner le repository
     2. Builder l'image Docker backend (via Dockerfile.backend)
     3. Builder l'image Docker frontend (via frontend-app/Dockerfile)
     4. Créer la base de données PostgreSQL
     5. Injecter les variables d'environnement
     6. Déployer les services
   - Durée: 5-10 minutes

8. **Vérifier le Déploiement**
   - Une fois terminé, vous verrez:
     - Status: "Deployed"
     - URL backend: `https://backend-xxxxx.ondigitalocean.app`
     - URL frontend: `https://e-compta-ia-light-xxxxx.ondigitalocean.app`

---

## 🚀 Méthode 2: Déploiement Manuel

Si vous préférez configurer manuellement (sans `.do/app.yaml`):

### Backend Service

1. **Créer le Service Backend**
   - Type: Docker
   - Source: GitHub repository
   - Dockerfile: `Dockerfile.backend`
   - HTTP Port: 8080
   - Instance: Basic ($5/mois)

2. **Variables d'environnement Backend**
   ```
   SPRING_PROFILES_ACTIVE=prod
   JWT_SECRET=<votre-clé-générée>
   JWT_EXPIRATION=86400000
   SPRING_JPA_HIBERNATE_DDL_AUTO=update
   SPRING_JPA_DATABASE_PLATFORM=org.hibernate.dialect.PostgreSQLDialect
   PORT=8080
   ```

3. **Connecter la Base de Données**
   - Les variables suivantes sont automatiquement injectées par DO:
     - `DATABASE_URL`
     - `USERNAME`
     - `PASSWORD`
   - Mapper vers Spring Boot:
     - `SPRING_DATASOURCE_URL=${DATABASE_URL}`
     - `SPRING_DATASOURCE_USERNAME=${USERNAME}`
     - `SPRING_DATASOURCE_PASSWORD=${PASSWORD}`

### Frontend Service

1. **Créer le Service Frontend**
   - Type: Docker
   - Source: GitHub repository
   - Dockerfile: `frontend-app/Dockerfile`
   - HTTP Port: 80
   - Instance: Basic ($5/mois)

2. **Variables d'environnement Frontend**
   ```
   REACT_APP_API_URL=https://backend-xxxxx.ondigitalocean.app
   ```
   ⚠️ Remplacer par l'URL réelle du backend

### Database

1. **Créer la Base de Données**
   - Engine: PostgreSQL
   - Version: 14 ou supérieure
   - Cluster: Dev ($7/mois) ou Production ($15/mois)
   - Région: Même que les services (NYC par défaut)

2. **Connecter à l'App**
   - Dans l'interface, lier la database aux services
   - DigitalOcean injecte automatiquement les credentials

---

## 🔧 Configuration Post-Déploiement

### 1. Vérifier les Services

```bash
# Vérifier le backend
curl https://backend-xxxxx.ondigitalocean.app/actuator/health

# Tester l'inscription
curl -X POST https://backend-xxxxx.ondigitalocean.app/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"Test123!","role":"USER"}'

# Tester la connexion
curl -X POST https://backend-xxxxx.ondigitalocean.app/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"Test123!"}'

# Vérifier le frontend
curl https://e-compta-ia-light-xxxxx.ondigitalocean.app
```

### 2. Configurer le Domaine Personnalisé (Optionnel)

1. Dans le dashboard DigitalOcean App Platform
2. Aller dans "Settings" → "Domains"
3. Cliquer sur "Add Domain"
4. Entrer votre domaine (ex: `app.votredomaine.com`)
5. Configurer les DNS selon les instructions DigitalOcean
6. SSL/TLS est configuré automatiquement

### 3. Activer les Alertes

1. Dans "Settings" → "Alerts"
2. Configurer les alertes pour:
   - CPU > 80%
   - Memory > 80%
   - App crashed
   - Deploy failed

---

## 📊 Monitoring et Logs

### Accéder aux Logs

1. **Via Dashboard**:
   - Sélectionner votre App
   - Cliquer sur le service (backend ou frontend)
   - Onglet "Runtime Logs"
   - Filtrer par niveau: Info, Warning, Error

2. **Via CLI** (optionnel):
   ```bash
   # Installer doctl
   brew install doctl  # macOS
   snap install doctl  # Linux
   
   # S'authentifier
   doctl auth init
   
   # Voir les logs
   doctl apps logs <app-id> --type run --follow
   ```

### Métriques Disponibles

- **CPU Usage**: Pourcentage d'utilisation CPU
- **Memory Usage**: Pourcentage d'utilisation mémoire
- **Request Count**: Nombre de requêtes HTTP
- **Response Time**: Temps de réponse moyen
- **Error Rate**: Taux d'erreur 5xx

### Diagnostics Courants

**Logs Backend Importants**:
```
✅ "Started EcomptaiaApplication" → Backend démarré
✅ "HikariPool-1 - Start completed" → Database connectée
✅ "Tomcat started on port 8080" → Serveur web prêt
❌ "Connection refused" → Database non accessible
❌ "JWT_SECRET" → Variable manquante
```

---

## 🐛 Troubleshooting

### Problème 1: Build Backend Échoue

**Symptômes**:
- Build failed dans les logs
- Erreur Maven

**Solutions**:
1. Vérifier que `Dockerfile.backend` existe à la racine
2. Vérifier que Java 17 est utilisé (configuré dans Dockerfile)
3. Consulter les logs de build détaillés
4. Vérifier que `backend/pom.xml` est accessible

### Problème 2: Backend Démarre puis Crash

**Symptômes**:
- Build réussit mais service en état "Failed"
- Logs montrent "Application startup failed"

**Causes courantes**:
1. **JWT_SECRET manquant**
   - Solution: Vérifier que JWT_SECRET est défini dans les variables d'environnement

2. **Base de données non connectée**
   - Solution: Vérifier que la database est "Available"
   - Vérifier que les credentials sont injectés correctement

3. **Mauvais profil Spring**
   - Solution: Vérifier que `SPRING_PROFILES_ACTIVE=prod` est défini

### Problème 3: Frontend Ne Peut Pas Se Connecter au Backend

**Symptômes**:
- Frontend charge mais erreurs CORS
- "Network Error" dans la console browser

**Solutions**:
1. **Vérifier REACT_APP_API_URL**
   - Doit pointer vers l'URL publique du backend
   - Format: `https://backend-xxxxx.ondigitalocean.app`

2. **CORS Configuration**
   - Backend doit autoriser l'origine du frontend
   - Vérifier la configuration CORS dans Spring Boot

3. **Rebuild du Frontend**
   - Les variables React doivent être définies au BUILD TIME
   - Après changement de REACT_APP_API_URL, redéployer le frontend

### Problème 4: Base de Données Non Accessible

**Symptômes**:
- "Connection refused" dans les logs backend
- "Authentication failed"

**Solutions**:
1. Vérifier que la database est dans la même région que les services
2. Vérifier que la database est "Available" (pas en maintenance)
3. Vérifier que les credentials sont correctement injectés
4. Vérifier la connexion dans les logs: regarder `SPRING_DATASOURCE_URL`

### Problème 5: Déploiement Lent

**Symptômes**:
- Build prend > 10 minutes

**Explications**:
- Premier build peut être long (téléchargement dépendances)
- Builds suivants sont plus rapides (cache)

**Optimisations**:
- Les Dockerfiles sont déjà optimisés en multi-stage
- DigitalOcean cache les layers Docker entre builds

---

## 💰 Tarification

### Configuration Minimale (Dev/Test)

| Ressource | Configuration | Prix/Mois |
|-----------|---------------|-----------|
| Backend | Basic (512MB RAM, 1 vCPU) | $5 |
| Frontend | Basic (512MB RAM, 1 vCPU) | $5 |
| Database | Dev (1GB RAM) | $7 |
| **Total** | | **$17/mois** |

### Configuration Production

| Ressource | Configuration | Prix/Mois |
|-----------|---------------|-----------|
| Backend | Professional (1GB RAM, 1 vCPU) | $12 |
| Frontend | Professional (1GB RAM, 1 vCPU) | $12 |
| Database | Production (2GB RAM) | $15 |
| **Total** | | **$39/mois** |

### Réduction de Coûts

1. **Période d'essai**: 30 jours gratuits
2. **Crédits promotionnels**: Souvent $100-200 pour nouveaux comptes
3. **Backend + Frontend combinés**: Servir le frontend depuis le backend (économise $5-12/mois)

---

## 📚 Comparaison avec Autres Plateformes

| Critère | DigitalOcean | Render | Railway |
|---------|--------------|--------|---------|
| **Prix/Mois** | $17 (dev) | $7-14 (dev) | $5-10 (dev) |
| **Build Speed** | ⭐⭐⭐ Moyen | ⭐⭐⭐⭐ Rapide | ⭐⭐⭐⭐ Rapide |
| **Monitoring** | ⭐⭐⭐⭐ Excellent | ⭐⭐⭐ Bon | ⭐⭐⭐ Bon |
| **Database Managed** | ⭐⭐⭐⭐⭐ Excellent | ⭐⭐⭐⭐ Très bon | ⭐⭐⭐⭐ Très bon |
| **Support Docker** | ⭐⭐⭐⭐⭐ Natif | ⭐⭐⭐⭐⭐ Natif | ⭐⭐⭐⭐ Via nixpacks |
| **Infrastructure as Code** | ✅ app.yaml | ✅ render.yaml | ✅ railway.json |
| **SSL/TLS Auto** | ✅ Oui | ✅ Oui | ✅ Oui |
| **Scaling** | ✅ Manuel/Auto | ✅ Manuel | ✅ Manuel |
| **Régions** | 8 datacenters | Multiple | Global |

### Quand Choisir DigitalOcean

✅ **Choisir DigitalOcean si**:
- Vous voulez un **monitoring robuste** intégré
- Vous avez déjà d'autres services DigitalOcean (Droplets, Spaces, etc.)
- Vous voulez une **base de données managée performante**
- Budget: $17-39/mois est acceptable

✅ **Choisir Render si**:
- Vous voulez la solution la **plus simple** (Blueprint automatique)
- Budget serré: $7-14/mois
- Monitoring de base suffit

✅ **Choisir Railway si**:
- Vous voulez le **plus rapide à déployer**
- Budget très serré: $5-10/mois
- Frontend intégré dans backend (un seul service)

---

## 🔐 Sécurité

### Bonnes Pratiques

1. **JWT_SECRET**
   - Toujours utiliser un secret fort (256+ bits)
   - Ne JAMAIS commit dans le code
   - Utiliser les "Encrypted Variables" de DigitalOcean

2. **Base de Données**
   - Activer "Trusted Sources" pour limiter les connexions
   - Backups automatiques activés (configuré dans DO)
   - Encryption at rest (inclus par défaut)

3. **HTTPS**
   - Certificats SSL/TLS automatiques (Let's Encrypt)
   - Force HTTPS activé par défaut
   - HSTS headers recommandés

4. **Variables d'Environnement**
   - Jamais de credentials en clair dans le code
   - Utiliser les "Environment Variables" de DigitalOcean
   - Marquer les secrets comme "SECRET" (encrypted)

---

## 🎓 Ressources

### Documentation Officielle
- [DigitalOcean App Platform Docs](https://docs.digitalocean.com/products/app-platform/)
- [App Spec Reference](https://docs.digitalocean.com/products/app-platform/reference/app-spec/)
- [Pricing](https://www.digitalocean.com/pricing/app-platform)

### Tutoriels
- [Getting Started with App Platform](https://docs.digitalocean.com/products/app-platform/getting-started/)
- [Deploying Docker Apps](https://docs.digitalocean.com/products/app-platform/how-to/deploy-docker-image/)

### Support
- [DigitalOcean Community](https://www.digitalocean.com/community/)
- [DigitalOcean Support Portal](https://cloud.digitalocean.com/support)
- [Status Page](https://status.digitalocean.com/)

---

## ✅ Checklist de Déploiement

Avant de créer un ticket de support, vérifier:

- [ ] `.do/app.yaml` existe à la racine du repository
- [ ] Repository GitHub est connecté à DigitalOcean
- [ ] JWT_SECRET est défini et encrypté
- [ ] Services sont définis: backend, frontend, database
- [ ] Dockerfiles existent et sont valides
- [ ] Variables d'environnement sont configurées
- [ ] Base de données est "Available"
- [ ] Derniers commits sont pushés sur la branche main
- [ ] Build local Docker réussit (test préalable)

---

## 🎉 Conclusion

DigitalOcean App Platform offre une solution complète et professionnelle pour déployer E-COMPTA-IA-LIGHT avec:

- ✅ Déploiement automatisé via `.do/app.yaml`
- ✅ Monitoring et métriques intégrés
- ✅ Base de données PostgreSQL managée
- ✅ SSL/TLS automatique
- ✅ Scaling facile

**Prochaine étape**: Suivre la **Méthode 1** ci-dessus pour déployer en quelques minutes !

---

## 🆘 Besoin d'Aide ?

- **Guide général**: Voir `DEPLOYMENT_GUIDE.md`
- **Problèmes Render**: Voir `RENDER_TROUBLESHOOTING.md`
- **Problèmes Railway**: Voir `RAILWAY_DEPLOYMENT.md`
- **Analyse complète**: Voir `ANALYSE_ISSUES_ET_DEPLOIEMENTS.md`
