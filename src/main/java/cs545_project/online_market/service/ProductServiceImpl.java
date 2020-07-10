package cs545_project.online_market.service;

import cs545_project.online_market.domain.Image;
import cs545_project.online_market.domain.Product;
import cs545_project.online_market.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;

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
    public void test() {
        Product product = new Product();
        product.setDescription("Product description");
        product.setImages(Arrays.asList(new Image("ssjabsgas")));
        product.setPrice(2333.3);
        product.setName("Iphone 11");
        product.setStock(100);

        productRepository.save(product);

        System.out.println(productRepository.findById(product.getId()));
    }
}
