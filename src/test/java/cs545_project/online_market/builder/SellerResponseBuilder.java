package cs545_project.online_market.builder;

import cs545_project.online_market.controller.response.ProductResponse;
import cs545_project.online_market.controller.response.SellerResponse;

public class SellerResponseBuilder {

    private SellerResponse sellerResponse;

 	public SellerResponseBuilder() {
		this.sellerResponse = new SellerResponse();
	}

    public SellerResponse build() {
        return sellerResponse;
    }

    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    public SellerResponseBuilder withId(Long id) {
        this.sellerResponse.setId(id);
        return  this;
    }

    public SellerResponseBuilder withFirstName(String firstName) {
        this.sellerResponse.setFirstName(firstName);
        return  this;
    }

    public SellerResponseBuilder withLastName(String lastName) {
        this.sellerResponse.setLastName(lastName);
        return  this;
    }

    public SellerResponseBuilder withEmail(String email) {
        this.sellerResponse.setEmail(email);
        return  this;
    }
}
