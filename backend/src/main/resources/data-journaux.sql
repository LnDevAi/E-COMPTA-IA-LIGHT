-- Ajout de données de test pour la table journaux
INSERT INTO journaux (id, code, libelle, type, actif) VALUES (random_uuid(), 'ACH', 'Journal des achats', 'ACHAT', true);
INSERT INTO journaux (id, code, libelle, type, actif) VALUES (random_uuid(), 'VEN', 'Journal des ventes', 'VENTE', true);
INSERT INTO journaux (id, code, libelle, type, actif) VALUES (random_uuid(), 'BAN', 'Journal de banque', 'BANQUE', true);
INSERT INTO journaux (id, code, libelle, type, actif) VALUES (random_uuid(), 'CAI', 'Journal de caisse', 'CAISSE', true);
INSERT INTO journaux (id, code, libelle, type, actif) VALUES (random_uuid(), 'OD', 'Journal opérations diverses', 'OPERATIONS_DIVERSES', true);
