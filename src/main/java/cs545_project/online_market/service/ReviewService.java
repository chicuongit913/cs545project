package cs545_project.online_market.service;

import cs545_project.online_market.controller.response.ReviewResponse;
import cs545_project.online_market.domain.Review;
import javassist.NotFoundException;

import java.util.List;

public interface ReviewService {
    List<ReviewResponse> getCreatedReviews();

    void postReview(long reviewId) throws NotFoundException;

    void declineReview(long reviewId) throws NotFoundException;
}

