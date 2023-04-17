package tr.edu.metu.ii.AnyChange.product.repositories;

import org.springframework.data.repository.CrudRepository;
import tr.edu.metu.ii.AnyChange.product.models.PriceSource;

public interface PriceSourceRepository extends CrudRepository<PriceSource, Long> {
}
