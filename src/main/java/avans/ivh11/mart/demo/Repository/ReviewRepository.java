package avans.ivh11.mart.demo.Repository;

import avans.ivh11.mart.demo.Domain.Review;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface ReviewRepository extends CrudRepository<Review, Long> {
}