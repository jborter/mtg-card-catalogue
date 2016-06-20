package mtg.cardCatalogue;

import mtg.cardCatalogue.domain.Card;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by jbo on 27.04.2016.
 */
@RepositoryRestResource(collectionResourceRel = "cards", path = "cards")
public interface CardRepository extends MongoRepository<Card, String>{
    public Card findByName(@Param("name") String name);
}
