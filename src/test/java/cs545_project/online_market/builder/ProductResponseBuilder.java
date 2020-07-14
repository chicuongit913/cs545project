package cs545_project.online_market.builder;

import cs545_project.online_market.controller.response.ProductResponse;
import cs545_project.online_market.controller.response.ReviewResponse;
import cs545_project.online_market.controller.response.SellerResponse;
import cs545_project.online_market.domain.Product;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductResponseBuilder {

     private ProductResponse productResponse;
    
 	public ProductResponseBuilder() {
		this.productResponse = new ProductResponse();
	}

    public ProductResponseBuilder withId(Long id) {
        this.productResponse.setId(id);
        return this;
    }

    public ProductResponseBuilder withName(String name) {
        this.productResponse.setName(name);
        return this;
    }

    public ProductResponseBuilder withDescription(String description) {
        this.productResponse.setDescription(description);
        return this;
    }

    public ProductResponseBuilder withPrice(Double price) {
        this.productResponse.setPrice(price);
        return this;
    }

    public ProductResponseBuilder withImage(String image) {
        this.productResponse.setImage(image);
        return this;
    }

    public ProductResponseBuilder withIsInUse(Boolean isInUse) {
        this.productResponse.setInUse(isInUse);
        return this;
    }

    public ProductResponseBuilder withReviews(List<ReviewResponse> reviewResponses) {
        this.productResponse.setReviews(reviewResponses);
        return this;
    }

    public ProductResponseBuilder withSellerResponse(SellerResponse sellerResponse) {
        this.productResponse.setSeller(sellerResponse);
        return this;
    }

    public ProductResponse build() {
        return productResponse;
    }

	
}
