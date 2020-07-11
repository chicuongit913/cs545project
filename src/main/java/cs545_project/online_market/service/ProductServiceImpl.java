package cs545_project.online_market.service;

import cs545_project.online_market.domain.Product;
import cs545_project.online_market.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author knguyen93
 */

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public void test() {
        Product product = new Product();
        product.setDescription("Product description");
        product.setPrice(2333.3);
        product.setName("Iphone 11");
        product.setStock(100);

        productRepository.save(product);

        System.out.println(productRepository.findById(product.getId()));
    }

    @Override
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public ArrayList<Product> getAll() {
        return (ArrayList<Product>) productRepository.findAll();
    }

    @Override
    public Product findById(Long productId) {
            Optional<Product> product = productRepository.findById(productId);
            return product.isPresent() ? product.get(): null;

    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }


}
