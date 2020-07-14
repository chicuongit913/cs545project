package cs545_project.online_market.builder;

import cs545_project.online_market.controller.response.ProductResponse;
import cs545_project.online_market.controller.response.SellerResponse;
import cs545_project.online_market.domain.Product;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class ProductResponseListBuilder {

	public ProductResponseBuilder productResponseBuilderOne = new ProductResponseBuilder()
            .withId(1L)
			.withName("Apple Iphone 6s +")
            .withDescription("Old product apple")
			.withPrice(340.34)
			.withImage("/images/product/iphone_6s.ipg")
			.withIsInUse(true)
			.withSellerResponse(new SellerResponseBuilder()
					.withId(1L)
					.withFirstName("John")
					.withLastName("Smith")
					.withEmail("john@miu.edu")
					.build())
			.withReviews(new ReviewResponseListBuilder().build());

	public ProductResponseBuilder productResponseBuilderTwo = new ProductResponseBuilder()
			.withId(2L)
			.withName("Apple Iphone 8+")
			.withDescription("Old product apple")
			.withPrice(540.34)
			.withImage("/images/product/iphone_8.ipg")
			.withIsInUse(true)
			.withSellerResponse(new SellerResponseBuilder()
					.withId(1L)
					.withFirstName("John")
					.withLastName("Smith")
					.withEmail("john@miu.edu")
					.build());

	public List<ProductResponse> build() {
	    ProductResponse first = productResponseBuilderOne.build();
        ProductResponse second = productResponseBuilderTwo.build();
 	    return (List<ProductResponse>) Arrays.asList(first, second);
	}

	public ProductResponseBuilder getProductResponseBuilderOne() {
		return productResponseBuilderOne;
	}

}
