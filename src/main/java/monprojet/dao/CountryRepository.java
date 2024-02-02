package monprojet.dao;

import monprojet.entity.PopulationPays;
import org.springframework.data.jpa.repository.JpaRepository;
import monprojet.entity.Country;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring 
//

public interface CountryRepository extends JpaRepository<Country, Integer> {

    public Country findByName(String name);

    @Query("SELECT SUM(c.population) FROM City c WHERE c.country.id = :id")
    public Integer getPopulationForCountry(Integer id);

    @Query("SELECT c.country.name as name, SUM(c.population) as population FROM City c GROUP BY c.country")
    public List<PopulationPays> getPopulationByCountry();


}
