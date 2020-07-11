package cs545_project.online_market.repository;

import cs545_project.online_market.domain.Product;
import org.springframework.data.repository.CrudRepository;

/**
 * @author knguyen93
 */
public interface ProductRepository extends CrudRepository<Product, Long> {
}
