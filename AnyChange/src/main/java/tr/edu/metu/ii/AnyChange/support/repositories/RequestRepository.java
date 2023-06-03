package tr.edu.metu.ii.AnyChange.support.repositories;

import org.springframework.data.repository.CrudRepository;
import tr.edu.metu.ii.AnyChange.support.model.Request;

public interface RequestRepository extends CrudRepository<Request, Long> {
}
