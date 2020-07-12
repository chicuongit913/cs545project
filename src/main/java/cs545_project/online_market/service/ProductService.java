package cs545_project.online_market.service;

import cs545_project.online_market.controller.request.ReviewRequest;
import cs545_project.online_market.controller.response.ProductResponse;
import cs545_project.online_market.domain.Product;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;


public interface ProductService {
    void test();
    void saveProduct(Product product);
    ArrayList<Product> getAll();
    Product findById(Long productId);
    ProductResponse getProductById(Long id);
    void deleteProduct(Long productId);
    ProductResponse postReview(Long id, ReviewRequest reviewRequest);
}
