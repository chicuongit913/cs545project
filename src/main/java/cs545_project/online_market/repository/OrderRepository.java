package cs545_project.online_market.repository;

import cs545_project.online_market.domain.Order;
import org.springframework.data.repository.CrudRepository;

/**
 * @author knguyen93
 */
public interface OrderRepository extends CrudRepository<Order, Long> {
}
