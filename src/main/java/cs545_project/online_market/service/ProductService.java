package cs545_project.online_market.service;

import cs545_project.online_market.domain.Product;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

/**
 * @author knguyen93
 */
public interface ProductService {
    public void saveProduct(Product product);
    public ArrayList<Product> getAll();
}
