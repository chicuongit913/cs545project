package cs545_project.online_market.builder;


import cs545_project.online_market.controller.response.ProductResponse;
import cs545_project.online_market.controller.response.ReviewResponse;
import cs545_project.online_market.domain.Review;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ReviewResponseListBuilder {

	public ReviewResponseBuilder reviewResponseBuilderOne = new ReviewResponseBuilder()
            .withId(1L)
            .withCreatedDate(new Date())
            .withReviewer("John Smith")
            .withText("Good Product");

    public ReviewResponseBuilder reviewResponseBuilderTwo = new ReviewResponseBuilder()
            .withId(1L)
            .withCreatedDate(new Date())
            .withReviewer("John Smith")
            .withText("Good Product");

	public List<ReviewResponse> build() {
        ReviewResponse first = reviewResponseBuilderOne.build();
        ReviewResponse second = reviewResponseBuilderTwo.build();
 	    return (List<ReviewResponse>) Arrays.asList(first, second);
	}

	public ReviewResponseBuilder getReviewResponseBuilderOne() {
		return reviewResponseBuilderOne;
	}

}
