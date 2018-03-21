package avans.ivh11.mart.demo.Repository;

import avans.ivh11.mart.demo.Domain.BaseOrder;
import org.springframework.data.repository.CrudRepository;

public interface BaseOrderRepository<T extends BaseOrder> extends CrudRepository<T, Long> {
}
