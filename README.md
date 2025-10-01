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

1. Créez un projet Railway
2. Ajoutez les variables d'environnement (voir ci-dessous)
3. Déployez le backend avec Dockerfile.backend
4. Déployez le frontend avec frontend-app/Dockerfile
5. Configurez les services Railway pour exposer les ports nécessaires

---

## Variables d'environnement

- `JWT_SECRET` : clé secrète pour les tokens JWT
- `JWT_EXPIRATION` : durée de validité du token (ex: 86400000)
- `SPRING_DATASOURCE_URL` : URL JDBC de la base (ex: jdbc:h2:mem:testdb)
- `SPRING_DATASOURCE_USERNAME` : utilisateur DB
- `SPRING_DATASOURCE_PASSWORD` : mot de passe DB
- `SPRING_PROFILES_ACTIVE` : profil Spring (ex: prod, dev)

---
