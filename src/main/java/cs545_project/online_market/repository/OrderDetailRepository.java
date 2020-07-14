package cs545_project.online_market.repository;

import cs545_project.online_market.domain.OrderDetails;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends CrudRepository<OrderDetails, Long> {
    @Query("SELECT od from  OrderDetails od " +
            "join Product p on od.product.id = p.id " +
            "join User u on p.seller.id = u.id " +
            "where u.id = :sellerId")
    List<OrderDetails> getOrderDetailCreated(@Param("sellerId") Long sellerId);
}
