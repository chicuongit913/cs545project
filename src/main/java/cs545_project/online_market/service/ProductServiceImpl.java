package cs545_project.online_market.service;

import cs545_project.online_market.controller.request.ProductRequest;
import cs545_project.online_market.controller.request.ReviewRequest;
import cs545_project.online_market.controller.response.ProductResponse;
import cs545_project.online_market.controller.response.ReviewResponse;
import cs545_project.online_market.domain.Product;
import cs545_project.online_market.domain.Review;
import cs545_project.online_market.domain.User;
import cs545_project.online_market.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * @author knguyen93
 */

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void saveProduct(ProductRequest productRequest, String path) {
        Product product = new Product();
        product.setImage(path);
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStock(productRequest.getStock());
        product.setCreatedDate(productRequest.getCreatedDate());
        product.setUpdatedDate(productRequest.getUpdatedDate());


        productRepository.save(product);
    }

    @Override
    public ArrayList<Product> getAllProducts() {

        return (ArrayList<Product>) productRepository.findAll();
    }

    @Override
    public Product findById(Long productId) {
        return productRepository.findById(productId).orElseGet(null);
    }

    @Override
    public ProductResponse getProductById(Long id) {
        return productRepository.findById(id)
            .map(ProductServiceImpl::apply)
            .orElseGet(ProductResponse::new);
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public void updateProduct(ProductRequest productRequest, String path) {
        Product product = new Product();

        product.setImage(path);
        product.setId(productRequest.getId());
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStock(productRequest.getStock());
        product.setUpdatedDate(new Date());

        productRepository.save(product);
    }

    public ProductResponse postReview(Long id, ReviewRequest reviewRequest) {
        Product product = productRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid Product Id"));
        product.addReview(new Review(reviewRequest.getReview()));
        return apply(productRepository.save(product));
    }

    private static ProductResponse apply(Product product) {
        ProductResponse response = new ProductResponse();
        BeanUtils.copyProperties(product, response, "reviews");
        response.setReviews(
            product.getReviews()
                .stream()
                .filter(Review::isValid)
                .map(r -> {
                    ReviewResponse reviewResponse = new ReviewResponse();
                    BeanUtils.copyProperties(r, reviewResponse);
                    return reviewResponse;
                })
                .collect(Collectors.toList())
        );
        return response;
    }
}
