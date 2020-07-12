package cs545_project.online_market.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No product")
public class ProductNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 3935230281455340039L;

	private Long productId;

	public ProductNotFoundException(Long productId) {
		this.productId = productId;
	}

	public Long getProductId() {
		return productId;
	}

}
