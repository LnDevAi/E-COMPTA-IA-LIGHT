# Multi-cloud deployment configuration for E-COMPTA-IA-LIGHT

## Supported Platforms

Render, Fly.io, Railway, Heroku, DigitalOcean App Platform

## Backend (Spring Boot)

Dockerfile.backend is compatible with all platforms

## Frontend (React)

Dockerfile in frontend-app is compatible with all platforms

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
