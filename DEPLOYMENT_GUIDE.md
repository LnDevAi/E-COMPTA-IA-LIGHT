# Multi-cloud deployment configuration for E-COMPTA-IA-LIGHT

## Supported Platforms

Render, Fly.io, Railway, Heroku, DigitalOcean App Platform

## Backend (Spring Boot)

Dockerfile.backend is compatible with all platforms (multi-stage build with Maven)

## Frontend (React)

Dockerfile in frontend-app is compatible with all platforms (multi-stage build with Nginx)

## Render (Recommandé - Configuration automatique via render.yaml)

### Déploiement automatique via Blueprint

Le fichier `render.yaml` à la racine du projet permet un déploiement automatique complet :

1. **Connecter le repository à Render**
   - Aller sur https://dashboard.render.com
   - Cliquer sur "New +" → "Blueprint"
   - Connecter votre repository GitHub
   - Sélectionner le repository E-COMPTA-IA-LIGHT

2. **Services créés automatiquement**
   - `ecompta-backend` : Service Web backend (Dockerfile.backend)
   - `ecompta-frontend` : Service Web frontend (frontend-app/Dockerfile)
   - `postgres` : Base de données PostgreSQL

3. **Variables d'environnement configurées automatiquement**
   - `SPRING_PROFILES_ACTIVE=prod`
   - `JWT_SECRET` : Généré automatiquement
   - `JWT_EXPIRATION=86400000`
   - Connexion PostgreSQL configurée automatiquement
   - `REACT_APP_API_URL` : URL du backend

4. **Déploiement continu**
   - Chaque push sur `main` déclenche un déploiement automatique
   - Les builds Docker se font sur l'infrastructure Render
   - Pas besoin de build local

### Configuration manuelle (alternative)

Si vous préférez configurer manuellement :

#### Backend
1. New Web Service
2. Connect repository
3. Name: `ecompta-backend`
4. Environment: Docker
5. Dockerfile path: `./Dockerfile.backend`
6. Instance type: Starter
7. Add environment variables:
   - `SPRING_PROFILES_ACTIVE=prod`
   - `JWT_SECRET=<generate-strong-secret>`
   - `JWT_EXPIRATION=86400000`
   - Connecter à la database PostgreSQL

#### Frontend
1. New Web Service
2. Connect repository
3. Name: `ecompta-frontend`
4. Environment: Docker
5. Dockerfile path: `./frontend-app/Dockerfile`
6. Instance type: Starter
7. Add environment variable:
   - `REACT_APP_API_URL=https://ecompta-backend.onrender.com`

#### Database
1. New PostgreSQL Database
2. Name: `postgres`
3. Database name: `ecomptaia`
4. User: `ecomptaia`
5. Instance type: Starter

## Railway

### railway.json
{
  "build": {
    "backend": "cd backend && mvn clean package",
    "frontend": "cd frontend-app && npm install && npm run build"
  },
  "deploy": {
    "backend": "docker build -f Dockerfile.backend -t backend . && docker run -p 8080:8080 backend",
    "frontend": "docker build -f frontend-app/Dockerfile -t frontend . && docker run -p 80:80 frontend"
  }
}


## Render

Add these services in Render dashboard

- Web Service: backend (Dockerfile.backend, port 8080)
- Web Service: frontend (frontend-app/Dockerfile, port 80)

[...existing code...]

## Fly.io

### fly.toml
[app]
name = "ecompta-ia-light"

[[services]]
internal_port = 8080
protocol = "tcp"

[[services.ports]]
port = 8080
handlers = ["http"]

[[services]]
internal_port = 80
protocol = "tcp"

[[services.ports]]
port = 80
handlers = ["http"]

[...existing code...]

## Heroku

### Procfile
web: java -jar backend/target/ecompta-ia-light-2.0.0.jar
frontend: cd frontend-app && npm run build && npx serve -s build

[...existing code...]

## DigitalOcean App Platform

Add two components

- Backend: Dockerfile.backend, port 8080
- Frontend: frontend-app/Dockerfile, port 80

[...existing code...]

## Notes
# - Pour chaque plateforme, configure les variables d'environnement (DB, JWT, etc.) dans le dashboard.
# - Les Dockerfiles sont universels pour tous les clouds.
# - Pour Heroku, le frontend peut être servi via npx serve ou via nginx (ajouter Dockerfile si besoin).
# - Pour Railway, Render, DigitalOcean, Fly.io, déclare les services dans l'interface et relie les ports.
