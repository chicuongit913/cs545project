package cs545_project.online_market.service;

import cs545_project.online_market.controller.request.ProductRequest;
import cs545_project.online_market.controller.request.ReviewRequest;
import cs545_project.online_market.controller.response.ProductResponse;
import cs545_project.online_market.controller.response.ReviewResponse;
import cs545_project.online_market.controller.response.SellerResponse;
import cs545_project.online_market.domain.Product;
import cs545_project.online_market.domain.Review;
import cs545_project.online_market.domain.User;
import cs545_project.online_market.domain.UserRole;
import cs545_project.online_market.helper.Util;
import cs545_project.online_market.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author knguyen93
 */

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    private static String uploadDirectory = System.getProperty("user.dir") + "/images/";

    private ProductRepository productRepository;
    private Util util;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, Util util) {
        this.productRepository = productRepository;
        this.util = util;
    }

    @Override
    public ProductResponse saveProduct(ProductRequest productRequest) throws IOException {
        User seller = Optional.ofNullable(util.getCurrentUser())
            .filter(u -> UserRole.SELLER.equals(u.getRole()))
            .orElseThrow(() -> new IllegalArgumentException("Only Seller can add new product"));

        String imagePath = "https://placeimg.com/500/500/tech?query=97";
        if (productRequest.getImage() != null && !productRequest.getImage().isEmpty()) {
            imagePath = util.generateImageName();
            String transferLocation = uploadDirectory + imagePath + ".png";
            productRequest.getImage().transferTo(new File(transferLocation));
        }
        Product product = new Product();
        product.setImage(imagePath);
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStock(productRequest.getStock());
        product.setSeller(seller);
        productRepository.save(product);
        return this.apply(product);
    }

    @Override
    public List<ProductResponse> getSellerProducts(Long id) {
        return productRepository.findBySeller(id)
            .stream()
            .map(this::apply)
            .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.getAllProducts()
            .stream()
            .map(this::apply)
            .collect(Collectors.toList());
    }

    @Override
    public Product findById(Long productId) {
        return productRepository.findById(productId).orElseGet(null);
    }

    @Override
    public ProductResponse getProductById(Long id) {
        return productRepository.findById(id)
            .map(this::apply)
            .orElseThrow(() -> new IllegalArgumentException("Invalid Product Id"));
    }

    @Override
    public void deleteProductByAdmin(Long productId) {
       Optional.ofNullable(util.getCurrentUser())
            .filter(u -> UserRole.ADMIN.equals(u.getRole()))
            .orElseThrow(() -> new IllegalArgumentException("Only admin can delete this Product"));
        productRepository.findById(productId)
            .filter(p -> !p.isInUse())
            .orElseThrow(() -> new IllegalArgumentException("This product already in used and cannot be deleted"));
        productRepository.deleteById(productId);
    }

    @Override
    public List<ProductResponse> getAllUnUsedProducts() {
        return productRepository.getAllUnUsedProducts()
            .stream()
            .map(this::apply)
            .collect(Collectors.toList());
    }

    @Override
    public void deleteProduct(Long productId) {
        User seller = Optional.ofNullable(util.getCurrentUser())
            .filter(u -> UserRole.SELLER.equals(u.getRole()))
            .orElseThrow(() -> new IllegalArgumentException("Only Seller can update product"));
        Product product = productRepository.findById(productId)
            .filter(p -> !p.isInUse())
            .orElseThrow(() -> new IllegalArgumentException("This product cannot be deleted"));
        Optional.ofNullable(product.getSeller())
            .filter(s -> seller.equals(s))
            .orElseThrow(() -> new IllegalArgumentException("This product does not belong to you"));

        productRepository.deleteById(productId);
    }

    @Override
    public ProductResponse updateProduct(ProductRequest productRequest) throws IOException {
        User seller = Optional.ofNullable(util.getCurrentUser())
            .filter(u -> UserRole.SELLER.equals(u.getRole()))
            .orElseThrow(() -> new IllegalArgumentException("Only Seller can update product"));

        String imagePath;
        if (productRequest.getImage() != null && !productRequest.getImage().isEmpty()) {
            imagePath = util.generateImageName();
            String transferLocation = uploadDirectory + imagePath + ".png";
            productRequest.getImage().transferTo(new File(transferLocation));
        } else {
            imagePath = "https://placeimg.com/500/500/tech?query=97";
        }

        Product product = productRepository.findById(productRequest.getId())
            .filter(p -> p.getSeller().equals(seller))
            .map(p -> {
                p.setStock(productRequest.getStock());
                p.setName(productRequest.getName());
                p.setDescription(productRequest.getDescription());
                p.setUpdatedDate(new Date());
                p.setPrice(productRequest.getPrice());
                p.setImage(imagePath);
                return p;
            })
            .orElseThrow(() -> new IllegalArgumentException("Invalid Product Id"));
        productRepository.save(product);
        return this.apply(product);
    }

    public ProductResponse postReview(Long id, ReviewRequest reviewRequest) {
        Product product = productRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid Product Id"));
        User buyer = Optional.ofNullable(util.getCurrentUser())
            .orElseThrow(() -> new IllegalArgumentException("Only Buyer can post product review"));
        Review review = new Review();
        review.setReviewer(buyer);
        review.setText(reviewRequest.getReview());
        product.addReview(review);
        return apply(productRepository.save(product));
    }

    private ProductResponse apply(Product product) {
        ProductResponse response = new ProductResponse();
        BeanUtils.copyProperties(product, response, "reviews", "seller");
        response.setSeller(this.apply(product.getSeller()));
        response.setReviews(
            product.getReviews()
                .stream()
                .filter(Review::isValid)
                .map(r -> {
                    ReviewResponse reviewResponse = new ReviewResponse();
                    BeanUtils.copyProperties(r, reviewResponse, "reviewer");
                    reviewResponse.setReviewer(r.getReviewer().getFullName());
                    return reviewResponse;
                })
                .collect(Collectors.toList())
        );
        return response;
    }

    private SellerResponse apply(User u) {
        SellerResponse seller = new SellerResponse();
        BeanUtils.copyProperties(u, seller);
        return seller;
    }
}
