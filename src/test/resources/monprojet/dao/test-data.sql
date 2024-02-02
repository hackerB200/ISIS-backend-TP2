-- Initialisation spécifiques pour un jeu de test
INSERT INTO Country(code, name) VALUES
    ('IT', 'Italie');

INSERT INTO City(country_id, population, name) VALUES
    ((SELECT id FROM Country WHERE name = 'Italie'), 382258, 'Florence');