package cs545_project.online_market.repository;

import cs545_project.online_market.domain.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author knguyen93
 */
@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

          @Query("select  distinct  p from Product p where p.seller.id = :id")
          ArrayList<Product> findBySeller(Long id);



}
