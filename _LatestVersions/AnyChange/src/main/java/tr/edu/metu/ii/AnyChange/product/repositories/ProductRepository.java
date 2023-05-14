package tr.edu.metu.ii.AnyChange.product.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import tr.edu.metu.ii.AnyChange.product.models.Product;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findByNameContainsIgnoreCase(String keyword);
}
