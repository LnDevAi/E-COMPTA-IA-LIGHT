-- Module Utilisateur & Abonnement
CREATE TABLE IF NOT EXISTS utilisateur (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    abonnement VARCHAR(50),
    modules VARCHAR(255),
    role VARCHAR(50) DEFAULT 'USER'
);

CREATE TABLE IF NOT EXISTS abonnement (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    abonnement VARCHAR(50),
    modules VARCHAR(255)
);
CREATE TABLE IF NOT EXISTS systemes_comptables (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(50) UNIQUE NOT NULL,
    libelle VARCHAR(255) NOT NULL,
    description VARCHAR(1000)
);
CREATE TABLE IF NOT EXISTS sycebnl_piece_justificative (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    libellePJ VARCHAR(255),
    datePiece DATE,
    typePJ VARCHAR(100),
    entrepriseId BIGINT,
    utilisateurId BIGINT,
    filePath VARCHAR(255),
    ocrResult TEXT,
    iaResult TEXT,
    status VARCHAR(50)
);
CREATE TABLE IF NOT EXISTS comptes_comptables (
    id UUID PRIMARY KEY,
    numero VARCHAR(20) NOT NULL,
    libelle VARCHAR(255) NOT NULL,
    type VARCHAR(50) NOT NULL,
    nature VARCHAR(50) NOT NULL,
    actif BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS journaux (
    id UUID PRIMARY KEY,
    code VARCHAR(10) UNIQUE NOT NULL,
    libelle VARCHAR(100) NOT NULL,
    type VARCHAR(30) NOT NULL,
    actif BOOLEAN NOT NULL
);

-- SYCEBNL MODULE
CREATE TABLE IF NOT EXISTS sycebnl_organization (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    country VARCHAR(100),
    registration_number VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS piece_justificative_sycebnl (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    organization_id BIGINT NOT NULL,
    filename VARCHAR(255) NOT NULL,
    filetype VARCHAR(50),
    upload_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (organization_id) REFERENCES sycebnl_organization(id) ON DELETE CASCADE
);
