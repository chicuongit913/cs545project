package cs545_project.online_market.repository;

import cs545_project.online_market.domain.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author knguyen93
 */
@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
}
