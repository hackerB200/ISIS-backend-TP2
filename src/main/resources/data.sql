-- Initialisation des tables
INSERT INTO Country(id, code, name) VALUES
-- Les clés sont auto-générées, donc on ne les spécifie pas
    (default, 'FR', 'France'), -- id = 1
    (default, 'UK', 'United Kingdom'), -- id = 2
    (default, 'US', 'United States of America'); -- id = 3
-- On peut fixer la valeur des clés auto-générées, mais il faudrait ensuite
-- réinitialiser le compteur de clé auto-générée
-- Attention : la syntaxe est différente selon le SGBD utilisé
-- ALTER TABLE Country ALTER COLUMN id RESTART WITH 4;

INSERT INTO City(country_id, id, population, name) VALUES
    (1, default, 2206488, 'Paris'),
    (2, default, 8825000, 'London'),
    (3, default, 8538000, 'New York'),
    (1, default, 479553, 'Toulouse'),
    (1, default, 257351, 'Bordeaux'),
    (1, default, 861635, 'Marseille');

