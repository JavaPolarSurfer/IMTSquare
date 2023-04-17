package tr.edu.metu.ii.AnyChange.product.repositories;

import org.springframework.data.repository.CrudRepository;
import tr.edu.metu.ii.AnyChange.product.models.PricePoint;

public interface PricePointRepository extends CrudRepository<PricePoint, Long> {
}
