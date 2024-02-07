package monprojet.dao;

import monprojet.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring 
//

public interface CityRepository extends JpaRepository<City, Integer> {

    public City findByName(String name);

    @Query ("SELECT COUNT(c) FROM City c WHERE c.country.id = :id")
    public Integer countCityByCountry(Integer id);

    @Query ("SELECT SUM(c.population) FROM City c WHERE c.country.id = :id")
    public Integer sumPopulationByCountry(Integer id);
}
