package cs545_project.online_market.controller.api;

import cs545_project.online_market.controller.api.response.ResponseObj;
import cs545_project.online_market.controller.request.ProductRequest;
import cs545_project.online_market.controller.request.ReviewRequest;
import cs545_project.online_market.controller.response.ProductResponse;
import cs545_project.online_market.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    private ProductService productService;
    private MessageSourceAccessor messageAccessor;

    @Autowired
    public ProductRestController(ProductService productService, MessageSourceAccessor messageAccessor) {
        this.productService = productService;
        this.messageAccessor = messageAccessor;
    }

    @GetMapping
    public ResponseEntity getAllProducts() {
        return ResponseEntity
            .ok(new ResponseObj<List>()
                .data(productService.getAllProducts()));
    }

    @GetMapping("/{productId}")
    public ResponseEntity getProduct(@PathVariable Long productId) {
        return ResponseEntity
            .ok(new ResponseObj<ProductResponse>()
                .data(productService.getProductById(productId)));
    }

    @PostMapping
    public ResponseEntity createProduct(@Valid  @RequestBody ProductRequest productRequest) throws IOException {
        return ResponseEntity
            .ok(new ResponseObj<ProductResponse>()
                .data(productService.saveProduct(productRequest))
                .message("Product is saved"));
    }

    @PutMapping("/{productId}")
    public ResponseEntity updateProduct(@PathVariable Long productId,
                                        @Valid @RequestBody ProductRequest productRequest) throws IOException {
        productRequest.setId(productId);
        return ResponseEntity
            .ok(new ResponseObj<ProductResponse>()
                .data(productService.updateProduct(productRequest))
                .message("Product is updated"));
    }

    @PostMapping("/{productId}/review")
    public ResponseEntity postProductReview(@PathVariable Long productId,
                                            @Valid @RequestBody ReviewRequest reviewRequest) {
        return ResponseEntity
            .ok(new ResponseObj<ProductResponse>()
                .data(productService.postReview(productId, reviewRequest))
                .message("Review is posted and waiting to be approved"));
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity handleApiException(Exception ex) {
        return ResponseEntity.badRequest()
            .body(new ResponseObj<>()
                .error(ex.getMessage())
                .message("Invalid request data")
            );
    }

    @ExceptionHandler({BindException.class})
    public ResponseEntity handleApiException(BindException ex) {
        return ResponseEntity.badRequest()
            .body(new ResponseObj<>()
                .errors(
                    ex.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(messageAccessor::getMessage)
                        .collect(Collectors.toList())
                ).message("Invalid Request data"));
    }
}
