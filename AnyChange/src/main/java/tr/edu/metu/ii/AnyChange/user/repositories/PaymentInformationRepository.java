package tr.edu.metu.ii.AnyChange.user.repositories;

import org.springframework.data.repository.CrudRepository;
import tr.edu.metu.ii.AnyChange.user.models.PaymentInformation;

public interface PaymentInformationRepository extends CrudRepository<PaymentInformation, Long> {
}
