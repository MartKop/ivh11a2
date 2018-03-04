package avans.ivh11.mart.demo.Repository;

import avans.ivh11.mart.demo.Domain.Product;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface ProductRepository extends CrudRepository<Product, Long> {
}
