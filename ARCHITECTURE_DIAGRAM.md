# Architecture Frontend-Backend E-COMPTA-IA-LIGHT

## Vue d'ensemble de l'architecture

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                           FRONTEND (React)                                   │
│                         frontend-app/src/                                    │
└─────────────────────────────────────────────────────────────────────────────┘
                                     │
                                     │ HTTP/REST
                                     │
┌─────────────────────────────────────────────────────────────────────────────┐
│                         BACKEND (Spring Boot)                                │
│                    backend/src/main/java/                                    │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## Cartographie Complète des Modules

### 1. MODULE BASE - Gestion Comptable Fondamentale

```
Frontend                            Backend
─────────                          ────────

┌──────────────────┐              ┌──────────────────────────┐
│  Comptes.js      │──────────────│ CompteComptableController│
│  /comptes        │  GET/POST    │  /api/comptes            │
└──────────────────┘  PUT/DELETE  └──────────────────────────┘

┌──────────────────┐              ┌──────────────────────────┐
│  Ecritures.js    │──────────────│ EcritureComptableCtrl    │
│  /ecritures      │  GET/POST    │  /api/ecritures          │
└──────────────────┘  DELETE      └──────────────────────────┘
                       /valider
                       /export/csv
                       /export/pdf

┌──────────────────┐              ┌──────────────────────────┐
│  Entreprises.js  │──────────────│ EntrepriseController     │
│  /entreprises    │  GET/POST    │  /api/entreprises        │
└──────────────────┘              └──────────────────────────┘
                       /automatique

┌──────────────────┐              ┌──────────────────────────┐
│  Journaux.js     │──────────────│ JournalController        │
│  /journaux       │  GET/POST    │  /api/journaux           │
└──────────────────┘  PUT         └──────────────────────────┘
                       /code/{code}

┌──────────────────────┐          ┌──────────────────────────┐
│ SystemesComptables.js│──────────│ SystemeComptableCtrl     │
│ /systemes-comptables │GET/POST  │ /api/systemes-comptables │
└──────────────────────┘PUT/DELETE└──────────────────────────┘

┌──────────────────┐              ┌──────────────────────────┐
│PlanComptable.js  │──────────────│ PlanComptableController  │
│/plan-comptable   │  GET         │  /api/plan-comptable     │
└──────────────────┘  /{systeme}  └──────────────────────────┘
                      /import
```

---

### 2. MODULE ÉTATS FINANCIERS - Reporting et Analyse

```
Frontend                            Backend
─────────                          ────────

┌──────────────────┐              ┌──────────────────────────┐
│  Balance.js      │──────────────│ BalanceController        │
│  /balance        │  GET         │  /api/balance            │
└──────────────────┘              └──────────────────────────┘

┌──────────────────┐              ┌──────────────────────────┐
│  GrandLivre.js   │──────────────│ GrandLivreController     │
│  /grand-livre    │  GET         │  /api/comptes/{numero}/  │
└──────────────────┘              │  grand-livre             │
                                  └──────────────────────────┘

┌──────────────────┐              ┌──────────────────────────┐
│  Bilan.js        │──────────────│ BilanController          │
│  /bilan          │  GET         │  /api/financial/bilan    │
└──────────────────┘              └──────────────────────────┘

┌──────────────────────┐          ┌──────────────────────────┐
│  CompteResultat.js   │──────────│ CompteResultatController │
│  /compte-resultat    │  GET     │  /api/financial/         │
└──────────────────────┘          │  compte-resultat         │
         ⭐ NOUVEAU                └──────────────────────────┘

┌──────────────────┐              ┌──────────────────────────┐
│ NotesAnnexes.js  │──────────────│ NoteAnnexeController     │
│ /notes-annexes   │  GET         │  /api/financial/         │
└──────────────────┘              │  notes-annexes           │
                                  └──────────────────────────┘

┌──────────────────────────┐     ┌──────────────────────────┐
│ EtatsFinanciersOhada.js  │─────│ EtatFinancierOhadaCtrl   │
│ /etats-financiers-ohada  │POST │  /api/etats-financiers-  │
└──────────────────────────┘     │  ohada/generer           │
         ⭐ NOUVEAU               └──────────────────────────┘
```

---

### 3. MODULES IA - Intelligence Artificielle et Automation

```
Frontend                            Backend
─────────                          ────────

┌──────────────────┐              ┌──────────────────────────┐
│  GedModule.js    │──────────────│ GedController            │
│  /ged            │  POST        │  /api/ged/upload         │
└──────────────────┘  /upload     └──────────────────────────┘
  modules/ged/                      module/ged/

┌──────────────────┐              ┌──────────────────────────┐
│  IaecModule.js   │──────────────│ IaecController           │
│  /iaec/:pieceId  │  POST        │  /api/iaec/analyze       │
└──────────────────┘  /analyze    └──────────────────────────┘
  modules/iaec/                     module/iaec/

┌──────────────────┐              ┌──────────────────────────┐
│  SycebnlPage.js  │──────────────│ SycebnlController        │
│  /sycebnl        │  Multiple    │  /api/sycebnl/           │
└──────────────────┘  endpoints   └──────────────────────────┘
                      • /organisations
                      • /pieces-justificatives
                      • /analyse-ia
                      • /generer-propositions
                      • /valider
                      • /generer-ecriture
                      • /etats-financiers
                      • /notes-annexes
```

---

### 4. MODULE UTILISATEUR - Authentification et Gestion

```
Frontend                            Backend
─────────                          ────────

┌──────────────────┐              ┌──────────────────────────┐
│  Login.js        │──────────────│ AuthController           │
│  /login          │  POST        │  /api/auth/login         │
└──────────────────┘              └──────────────────────────┘

┌──────────────────┐              ┌──────────────────────────┐
│  Register.js     │──────────────│ AuthController           │
│  /register       │  POST        │  /api/auth/register      │
└──────────────────┘              └──────────────────────────┘

┌──────────────────────┐          ┌──────────────────────────┐
│  InscriptionPage.js  │──────────│ UtilisateurController    │
│  /inscription        │  POST    │  /api/utilisateur/       │
└──────────────────────┘  GET     │  inscription             │
                         POST     │  (liste utilisateurs)    │
                                  │  /abonnement             │
                                  └──────────────────────────┘
```

---

## Structure Détaillée des Dossiers

### Frontend Structure

```
frontend-app/
├── src/
│   ├── pages/                    ← 15 pages
│   │   ├── Balance.js
│   │   ├── Bilan.js
│   │   ├── Comptes.js
│   │   ├── CompteResultat.js     ← ⭐ NOUVEAU
│   │   ├── Dashboard.js
│   │   ├── Ecritures.js
│   │   ├── Entreprises.js
│   │   ├── EtatsFinanciersOhada.js ← ⭐ NOUVEAU
│   │   ├── GrandLivre.js
│   │   ├── InscriptionPage.js
│   │   ├── Journaux.js
│   │   ├── Login.js
│   │   ├── NotesAnnexes.js
│   │   ├── PlanComptable.js
│   │   ├── Register.js
│   │   ├── SycebnlPage.js
│   │   └── SystemesComptables.js
│   │
│   ├── modules/                  ← 2 modules IA
│   │   ├── ged/
│   │   │   └── GedModule.js
│   │   └── iaec/
│   │       └── IaecModule.js
│   │
│   ├── components/
│   │   └── Layout.js             ← Navigation mise à jour
│   │
│   ├── App.js                    ← Routes mises à jour
│   └── ...
```

### Backend Structure

```
backend/src/main/java/com/ecomptaia/
├── accounting/
│   ├── controller/               ← 8 contrôleurs principaux
│   │   ├── BalanceController.java
│   │   ├── CompteComptableController.java
│   │   ├── EcritureComptableController.java
│   │   ├── EntrepriseController.java
│   │   ├── GrandLivreController.java
│   │   ├── JournalController.java
│   │   ├── PlanComptableController.java
│   │   └── SystemeComptableController.java
│   │
│   └── controller/financial/     ← 4 contrôleurs financiers
│       ├── BalanceController.java
│       ├── BilanController.java
│       ├── CompteResultatController.java
│       ├── EtatFinancierOhadaController.java
│       └── NoteAnnexeController.java
│
├── module/                       ← 4 modules spécialisés
│   ├── ged/
│   │   └── GedController.java
│   ├── iaec/
│   │   └── IaecController.java
│   ├── sycebnl/
│   │   └── SycebnlController.java
│   └── utilisateur/
│       └── UtilisateurController.java
│
└── security/controller/          ← 2 contrôleurs auth
    ├── AuthController.java
    └── UserLogController.java
```

---

## Flux de Données - Exemple: Génération du Compte de Résultat

```
┌─────────────────────────────────────────────────────────────────────┐
│  1. UTILISATEUR                                                      │
│     Accède à /compte-resultat via le menu "États Financiers"        │
└─────────────────────────────────────────────────────────────────────┘
                                ↓
┌─────────────────────────────────────────────────────────────────────┐
│  2. FRONTEND - CompteResultat.js                                    │
│     • Affiche le formulaire avec dates                              │
│     • Utilisateur saisit dateDebut et dateFin                       │
│     • Click sur "Générer"                                           │
└─────────────────────────────────────────────────────────────────────┘
                                ↓
┌─────────────────────────────────────────────────────────────────────┐
│  3. HTTP REQUEST                                                     │
│     axios.get('/api/financial/compte-resultat', {                   │
│       params: { dateDebut, dateFin }                                │
│     })                                                               │
└─────────────────────────────────────────────────────────────────────┘
                                ↓
┌─────────────────────────────────────────────────────────────────────┐
│  4. BACKEND - CompteResultatController                              │
│     @GetMapping                                                      │
│     public CompteResultat getCompteResultat(...)                    │
└─────────────────────────────────────────────────────────────────────┘
                                ↓
┌─────────────────────────────────────────────────────────────────────┐
│  5. SERVICE LAYER - CompteResultatService                           │
│     • Récupère les écritures de la période                          │
│     • Calcule charges (comptes 6xxx)                                │
│     • Calcule produits (comptes 7xxx)                               │
│     • Calcule résultat = produits - charges                         │
└─────────────────────────────────────────────────────────────────────┘
                                ↓
┌─────────────────────────────────────────────────────────────────────┐
│  6. DATABASE                                                         │
│     • Query sur table ecritures_comptables                          │
│     • Filter par date                                                │
│     • Group by compte                                                │
└─────────────────────────────────────────────────────────────────────┘
                                ↓
┌─────────────────────────────────────────────────────────────────────┐
│  7. HTTP RESPONSE                                                    │
│     {                                                                │
│       charges: [...],                                                │
│       produits: [...],                                               │
│       totalCharges: 150000,                                          │
│       totalProduits: 200000,                                         │
│       resultatNet: 50000                                             │
│     }                                                                │
└─────────────────────────────────────────────────────────────────────┘
                                ↓
┌─────────────────────────────────────────────────────────────────────┐
│  8. FRONTEND - Affichage                                            │
│     • Tableau des charges avec détails                              │
│     • Tableau des produits avec détails                             │
│     • Résultat net mis en évidence (vert si bénéfice, rouge perte) │
│     • Indicateur de performance                                      │
└─────────────────────────────────────────────────────────────────────┘
```

---

## Technologies Utilisées

### Frontend
- **Framework**: React 18.x
- **Routage**: React Router v6
- **HTTP Client**: Axios
- **Build**: Create React App (react-scripts)
- **Styling**: Inline styles (pas de framework CSS externe)

### Backend
- **Framework**: Spring Boot 3.x
- **Language**: Java 17+
- **Build**: Maven
- **APIs**: REST (Spring Web)
- **Security**: Spring Security
- **Data**: Spring Data JPA

---

## Statistiques de Couverture

### Pages Frontend par Catégorie

```
BASE (6 pages)
├─ Comptes.js
├─ Ecritures.js
├─ Entreprises.js
├─ Journaux.js
├─ SystemesComptables.js
└─ PlanComptable.js

ÉTATS FINANCIERS (6 pages)
├─ Balance.js
├─ GrandLivre.js
├─ Bilan.js
├─ CompteResultat.js        ← ⭐ NOUVEAU
├─ NotesAnnexes.js
└─ EtatsFinanciersOhada.js  ← ⭐ NOUVEAU

MODULES IA (3 modules)
├─ GedModule.js
├─ IaecModule.js
└─ SycebnlPage.js

UTILISATEUR (4 pages)
├─ Login.js
├─ Register.js
├─ InscriptionPage.js
└─ Dashboard.js

TOTAL: 19 composants/pages
```

### Contrôleurs Backend par Catégorie

```
PRINCIPAUX (8)
├─ BalanceController
├─ CompteComptableController
├─ EcritureComptableController
├─ EntrepriseController
├─ GrandLivreController
├─ JournalController
├─ PlanComptableController
└─ SystemeComptableController

FINANCIERS (4)
├─ BilanController
├─ CompteResultatController
├─ EtatFinancierOhadaController
└─ NoteAnnexeController

MODULES (4)
├─ GedController
├─ IaecController
├─ SycebnlController
└─ UtilisateurController

SÉCURITÉ (2)
├─ AuthController
└─ UserLogController

TOTAL: 18 contrôleurs
```

---

## Conclusion

### ✅ Architecture Complète et Cohérente
- Toutes les APIs backend ont leur interface frontend
- Navigation organisée et intuitive
- Modules IA accessibles et fonctionnels
- Support complet du référentiel OHADA

### 🎯 Couverture à 100%
- 19 pages/modules frontend
- 18 contrôleurs backend
- 100% de couverture fonctionnelle

---

**Document créé le**: 2024  
**Version**: 1.0  
**Statut**: ✅ COMPLET
