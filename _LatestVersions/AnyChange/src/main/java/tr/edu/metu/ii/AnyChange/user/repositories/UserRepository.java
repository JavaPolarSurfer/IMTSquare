package tr.edu.metu.ii.AnyChange.user.repositories;

import org.springframework.data.repository.CrudRepository;
import tr.edu.metu.ii.AnyChange.user.models.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
