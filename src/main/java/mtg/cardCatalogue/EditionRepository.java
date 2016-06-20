package mtg.cardCatalogue;

import mtg.cardCatalogue.domain.Edition;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by jbo on 31.05.2016.
 */
@RepositoryRestResource(collectionResourceRel = "editions", path = "editions")
public interface EditionRepository extends MongoRepository<Edition, String> {
    public Edition findBySet(@Param("set") String set);
}
