package mtg.cardCatalogue;

import mtg.cardCatalogue.domain.Card;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

/**
 * Created by jbo on 27.04.2016.
 */
@RepositoryRestResource(collectionResourceRel = "cards", path = "cards")
public interface CardRepository extends MongoRepository<Card, String>{
    public List<Card> findByNameIgnoreCaseLike(@Param("name") String name);
}