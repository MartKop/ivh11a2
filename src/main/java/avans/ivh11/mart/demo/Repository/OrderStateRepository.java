package avans.ivh11.mart.demo.Repository;

import avans.ivh11.mart.demo.Domain.OrderState.OrderState;
import org.springframework.data.repository.CrudRepository;

public interface OrderStateRepository<T extends OrderState> extends CrudRepository<T, Long> {
}
