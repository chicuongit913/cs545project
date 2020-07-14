package cs545_project.online_market.builder;

import cs545_project.online_market.controller.response.ProductResponse;
import cs545_project.online_market.controller.response.ReviewResponse;
import cs545_project.online_market.controller.response.SellerResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

public class ReviewResponseBuilder {

    private ReviewResponse reviewResponse;

    public ReviewResponseBuilder() {
        this.reviewResponse = new ReviewResponse();
    }

    public ReviewResponse build() {
        return reviewResponse;
    }

    public ReviewResponseBuilder withId(long id) {
        this.reviewResponse.setId(id);
        return this;
    }

    public ReviewResponseBuilder withText(String text) {
        this.reviewResponse.setText(text);
        return this;
    }

    public ReviewResponseBuilder withCreatedDate(Date createdDate) {
        this.reviewResponse.setCreatedDate(createdDate);
        return this;
    }

    public ReviewResponseBuilder withReviewer(String reviewer) {
        this.reviewResponse.setReviewer(reviewer);
        return this;
    }
}
