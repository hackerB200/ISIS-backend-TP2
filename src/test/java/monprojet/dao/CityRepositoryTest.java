package monprojet.dao;

import lombok.extern.log4j.Log4j2;
import monprojet.entity.City;
import monprojet.entity.Country;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@Log4j2 // Génère le 'logger' pour afficher les messages de trace
@DataJpaTest
@Sql("test-data.sql")
public class CityRepositoryTest {

    @Autowired
    private CityRepository cityDAO;

    @Autowired
    private CountryRepository countryDAO;

    @Test
    public void onSaitCompterLesEnregistrements() {
        log.info("On compte les enregistrements de la table 'City'");
        int combienDeVillesDansLeJeuDeTest = 6 + 2; // 3 dans data.sql, 1 dans test-data.sql
        long nombre = cityDAO.count();
        assertEquals(combienDeVillesDansLeJeuDeTest, nombre, "On doit trouver 4 villes" );
    }

    @Test
    public void onSaitCompterLesVillesDUnPays() {
        log.info("On compte les villes d'un pays");
        Country france = countryDAO.findByName("France");
        Integer combienDeVillesDansLeJeuDeTest = france.getCities().size();
        Integer nombre = cityDAO.countCityByCountry(france.getId());
        assertEquals(combienDeVillesDansLeJeuDeTest, nombre, "On doit trouver "+combienDeVillesDansLeJeuDeTest+" villes pour le pays 'France'" );
    }

    @Test
    public void onSaitChercherUneVilleParNom() {
        log.info("On cherche une ville par son nom");
        String nomAttendu = "Paris";
        String nomTrouve = cityDAO.findByName("Paris").getName();
        assertEquals(nomAttendu, nomTrouve, "On doit trouver la ville 'Paris'" );
    }

    @Test
    void onTrouveLesVillesDesPays() {
        log.info("On vérifie que les villes d'un pays sont accessibles");
        City paris = cityDAO.findByName("Paris");
        Country france = countryDAO.findById(1).orElseThrow();
        assertTrue( france.getCities().contains(paris), "France contient Paris");
    }

    @Test
    public void onSaitCalculerLaPopulationDUnPays() {
        log.info("On vérifie que la population d'un pays est correcte");
        Integer populationAttendue = cityDAO.findByName("Paris").getPopulation() + cityDAO.findByName("Marseille").getPopulation() + cityDAO.findByName("Toulouse").getPopulation() + cityDAO.findByName("Bordeaux").getPopulation();
        Integer populationTrouvee = cityDAO.sumPopulationByCountry(1);
        assertEquals(populationAttendue, populationTrouvee, "On doit trouver la population de la France" );
    }

}
