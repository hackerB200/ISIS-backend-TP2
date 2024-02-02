package monprojet.entity;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import lombok.*;

// Un exemple d'entité
// On utilise Lombok pour auto-générer getter / setter / toString...
// cf. https://examples.javacodegeeks.com/spring-boot-with-lombok/
@Getter @Setter @NoArgsConstructor @RequiredArgsConstructor @ToString
@Entity // Une entité JPA
public class Country {
    // Identifiant technique
    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Integer id;
    // Identifiant métier (code ISO)
    @Column(unique=true)
    @NonNull
    private String code;
    
    @Column(unique=true)
    @NonNull
    private String name;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private List<City> cities = new ArrayList<>();
}
