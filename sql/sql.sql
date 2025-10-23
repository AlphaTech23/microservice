-- Supprimer la base si elle existe
DROP DATABASE IF EXISTS banque;

-- Creer la base
CREATE DATABASE banque
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'fr_FR.UTF-8'
    LC_CTYPE = 'fr_FR.UTF-8'
    TEMPLATE = template0;

-- Se connecter à la base creee
\c banque;

-----------------------------------------------------------
-- Table Direction
-----------------------------------------------------------
DROP TABLE IF EXISTS direction CASCADE;
CREATE TABLE direction (
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(100) NOT NULL,
    niveau INT NOT NULL
);

-----------------------------------------------------------
-- Table Utilisateur
-----------------------------------------------------------
DROP TABLE IF EXISTS utilisateur CASCADE;
CREATE TABLE utilisateur (
    id SERIAL PRIMARY KEY,
    login VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    id_direction INT NOT NULL REFERENCES direction(id),
    role INT NOT NULL
);

-----------------------------------------------------------
-- Table Action_role
-----------------------------------------------------------
DROP TABLE IF EXISTS action_role CASCADE;
CREATE TABLE action_role (
    id SERIAL PRIMARY KEY,
    nom_table VARCHAR(50) NOT NULL,
    action VARCHAR(50) NOT NULL,
    role INT NOT NULL
);

-----------------------------------------------------------
-- Table Type_mouvement_courant
-----------------------------------------------------------
DROP TABLE IF EXISTS type_mouvement_courant CASCADE;
CREATE TABLE type_mouvement_courant (
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(100) NOT NULL,
    type_solde INT NOT NULL CHECK (type_solde IN (-1, 1))
);

-----------------------------------------------------------
-- Table Client
-----------------------------------------------------------
DROP TABLE IF EXISTS client CASCADE;
CREATE TABLE client (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL
);

-----------------------------------------------------------
-- Table Compte_courant
-----------------------------------------------------------
DROP TABLE IF EXISTS compte_courant CASCADE;
CREATE TABLE compte_courant (
    id SERIAL PRIMARY KEY,
    id_client INT NOT NULL REFERENCES client(id),
    numero_compte VARCHAR(30) UNIQUE NOT NULL,
    solde DECIMAL(18,2) DEFAULT 0.00,
    date_ouverture DATE DEFAULT CURRENT_DATE,
    decouvert_autorise DECIMAL(18,2) DEFAULT 0.00
);

-----------------------------------------------------------
-- Table Mouvement_courant
-----------------------------------------------------------
DROP TABLE IF EXISTS mouvement_courant CASCADE;
CREATE TABLE mouvement_courant (
    id SERIAL PRIMARY KEY,
    id_type_mouvement INT NOT NULL REFERENCES type_mouvement_courant(id),
    id_compte INT NOT NULL REFERENCES compte_courant(id),
    id_client INT NOT NULL REFERENCES client(id),
    date_mouvement TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    montant NUMERIC(15,2) NOT NULL
);

-----------------------------------------------------------
-- Table Statut
-----------------------------------------------------------
DROP TABLE IF EXISTS statut CASCADE;
CREATE TABLE statut (
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL
);

-----------------------------------------------------------
-- Table Statut_mouvement
-----------------------------------------------------------
DROP TABLE IF EXISTS statut_mouvement CASCADE;
CREATE TABLE statut_mouvement (
    id_mouvement INT NOT NULL REFERENCES mouvement_courant(id),
    id_statut INT NOT NULL REFERENCES statut(id),
    id_utilisateur INT NOT NULL REFERENCES utilisateur(id),
    date_changement TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id_mouvement, id_statut, id_utilisateur)
);

-- =======================================================
--   BASE DE DONNeES BANQUE - DONNeES D’EXEMPLE
-- =======================================================

-- Directions
INSERT INTO direction (libelle, niveau) VALUES
('compte-courant', 1),
('compte-depot', 2),
('prets', 3);

-- Utilisateurs
INSERT INTO utilisateur (login, password, id_direction, role) VALUES
('admin', 'admin123', 1, 5),
('secretaire', 'secret123', 1, 2),
('agent1', 'agent123', 2, 2);

-- Actions possibles par role (exemple simplifie)
INSERT INTO action_role (nom_table, action, role) VALUES
('mouvement_courant', 'insert', 1),
('mouvement_courant', 'validate', 2);

-- Types de mouvements
INSERT INTO type_mouvement_courant (libelle, type_solde) VALUES
('Depot', 1),
('Retrait', -1),
('Frais Bancaires', -1),
('Virement Entrant', 1),
('Virement Sortant', -1);

-- Clients  
INSERT INTO client (nom) VALUES
('Rakoto Andry'),
('Rabe Marie'),
('Randria Jean'),
('Rakotomalala Lova'),
('Andriamanga Tiana');

-- Comptes courants
INSERT INTO compte_courant (id_client, numero_compte, solde, date_ouverture, decouvert_autorise) VALUES
(1, 'CC100001', 250000.00, '2023-01-15', 50000.00),
(2, 'CC100002', 50000.00, '2023-03-12', 10000.00),
(3, 'CC100003', -2000.00, '2023-05-05', 10000.00),
(4, 'CC100004', 750000.00, '2024-02-10', 100000.00),
(5, 'CC100005', 15000.00, '2024-09-20', 5000.00);

-- Mouvements
INSERT INTO mouvement_courant (id_type_mouvement, id_compte, id_client, date_mouvement, montant) VALUES
(1, 1, 1, '2024-01-15 09:30:00', 100000.00), -- Depot
(2, 1, 1, '2024-02-10 14:20:00', 50000.00),  -- Retrait
(4, 2, 2, '2024-03-01 08:15:00', 20000.00),  -- Virement entrant
(3, 3, 3, '2024-05-02 10:00:00', 5000.00),   -- Frais bancaires
(5, 4, 4, '2024-09-15 11:45:00', 25000.00),  -- Virement sortant
(1, 5, 5, '2024-10-01 15:00:00', 10000.00);  -- Depot

-- Statuts des mouvements
INSERT INTO statut (libelle) VALUES
('En attente'),
('Valide'),
('Rejete');

-- Liaisons statut-mouvement (exemples)
INSERT INTO statut_mouvement (id_mouvement, id_statut, id_utilisateur, date_changement) VALUES
(1, 2, 1, '2024-01-15 10:00:00'), -- Valide par admin
(2, 1, 2, '2024-02-10 15:00:00'), -- En attente (secretaire)
(3, 2, 1, '2024-03-01 09:00:00'),
(4, 3, 1, '2024-05-02 11:00:00'),
(5, 2, 1, '2024-09-15 12:00:00'),
(6, 2, 2, '2024-10-01 16:00:00');

-- =======================================================
--   VeRIFICATION RAPIDE
-- =======================================================
-- SELECT * FROM client;
-- SELECT * FROM compte_courant;
-- SELECT * FROM mouvement_courant;
-- SELECT * FROM statut_mouvement;

