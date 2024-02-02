package monprojet.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import monprojet.entity.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@Log4j2 // Génère le 'logger' pour afficher les messages de trace
@DataJpaTest
@Sql("test-data.sql")
public class CountryRepositoryTest {

    @Autowired
    private CountryRepository countryDAO;

    @Autowired
    private CityRepository cityDAO;

    @Test
    public void shouldGetPopulationForOneCountry() {
        log.info("On vérifie que la population d'un pays est correcte");
        Integer populationAttendue = cityDAO.findByName("Paris").getPopulation() + cityDAO.findByName("Toulouse").getPopulation();
        Integer populationTrouvee = countryDAO.getPopulationForCountry(1);
        assertEquals(populationAttendue, populationTrouvee, "On doit trouver la population de la France" );
    }

    @Test
    public void shouldGetListPopulationByCountry() {
        log.info("On vérifie que la population par pays est correcte");
        Integer populationFrance = cityDAO.findByName("Paris").getPopulation() + cityDAO.findByName("Toulouse").getPopulation();
        Integer populationItalie = cityDAO.findByName("Florence").getPopulation();
        Integer populationUK = cityDAO.findByName("London").getPopulation();
        Integer populationUSA = cityDAO.findByName("New York").getPopulation();

        //doit renvoyer une liste avec le nom du pays et sa population pour chacun des pays
        List<PopulationPays> listeTrouvee = countryDAO.getPopulationByCountry();
        for (PopulationPays pp : listeTrouvee) {
            if (pp.getName().equals("France")) {
                assertEquals(populationFrance, pp.getPopulation(), "On doit trouver la population de la France" );
            } else if (pp.getName().equals("Italie")) {
                assertEquals(populationItalie, pp.getPopulation(), "On doit trouver la population de l'Italie" );
            } else if (pp.getName().equals("United Kingdom")) {
                assertEquals(populationUK, pp.getPopulation(), "On doit trouver la population du Royaume-Uni" );
            } else if (pp.getName().equals("United States of America")) {
                assertEquals(populationUSA, pp.getPopulation(), "On doit trouver la population des USA" );
            } else {
                fail("On ne doit pas trouver d'autre pays");
            }
        }
    }

    @Test
    void lesNomsDePaysSontTousDifferents() {
        log.info("On vérifie que les noms de pays sont tous différents ('unique') dans la table 'Country'");
        
        Country paysQuiExisteDeja = new Country("XX", "France");
        try {
            countryDAO.save(paysQuiExisteDeja); // On essaye d'enregistrer un pays dont le nom existe
            fail("On doit avoir une violation de contrainte d'intégrité (unicité)");
        } catch (DataIntegrityViolationException e) {
            // Si on arrive ici, c'est normal, l'exception attendue s'est produite
        }
    }

    @Test
    void onSaitCompterLesEnregistrements() {
        log.info("On compte les enregistrements de la table 'Country'");
        int combienDePaysDansLeJeuDeTest = 3 + 1; // 3 dans data.sql, 1 dans test-data.sql
        long nombre = countryDAO.count();
        assertEquals(combienDePaysDansLeJeuDeTest, nombre, "On doit trouver 4 pays" );
    }

}
