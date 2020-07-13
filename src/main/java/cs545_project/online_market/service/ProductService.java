package cs545_project.online_market.service;

import cs545_project.online_market.controller.request.ProductRequest;
import cs545_project.online_market.controller.request.ReviewRequest;
import cs545_project.online_market.controller.response.ProductResponse;
import cs545_project.online_market.domain.Product;

import java.util.ArrayList;


public interface ProductService {
    public void saveProduct(ProductRequest productRequest, String path);
    public ArrayList<Product> getAllProducts();
    public Product findById(Long productId);
    ProductResponse getProductById(Long id);
    public void deleteProduct(Long productId);
    public void updateProduct(ProductRequest productRequest, String path);
    ProductResponse postReview(Long id, ReviewRequest reviewRequest);
}
