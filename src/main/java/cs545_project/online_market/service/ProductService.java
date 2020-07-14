package cs545_project.online_market.service;

import cs545_project.online_market.controller.request.ProductRequest;
import cs545_project.online_market.controller.request.ReviewRequest;
import cs545_project.online_market.controller.response.ProductResponse;
import cs545_project.online_market.domain.Product;
import cs545_project.online_market.domain.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public interface ProductService {
    void saveProduct(ProductRequest productRequest) throws IOException ;
    List<ProductResponse> getSellerProducts(Long id);
    List<ProductResponse> getAllProducts();
    Product findById(Long productId);
    ProductResponse getProductById(Long id);
    void deleteProduct(Long productId);
    void updateProduct(ProductRequest productRequest) throws IOException;
    ProductResponse postReview(Long id, ReviewRequest reviewRequest);
    void deleteProductByAdmin(Long productId);
    List<ProductResponse> getAllUnUsedProducts();
}
