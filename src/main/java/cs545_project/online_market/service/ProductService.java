package cs545_project.online_market.service;

import cs545_project.online_market.controller.request.ProductRequest;
import cs545_project.online_market.controller.request.ReviewRequest;
import cs545_project.online_market.controller.response.ProductResponse;
import cs545_project.online_market.domain.Product;
import cs545_project.online_market.domain.User;

import java.util.ArrayList;
import java.util.Date;


public interface ProductService {
    void saveProduct(ProductRequest productRequest, String path, User seller);
    ArrayList<Product> getAllProducts();
    ArrayList<Product> getSellerProducts(Long id);
    Product findById(Long productId);
    ProductResponse getProductById(Long id);
    void deleteProduct(Long productId);
    void updateProduct(ProductRequest productRequest, String path, User seller, Date creationDate);
    ProductResponse postReview(Long id, ReviewRequest reviewRequest);
}
