package tr.edu.metu.ii.AnyChange.product.repositories;

import org.springframework.data.repository.CrudRepository;
import tr.edu.metu.ii.AnyChange.product.models.PriceInformation;

public interface PriceInformationRepository extends CrudRepository<PriceInformation, Long> {
}
