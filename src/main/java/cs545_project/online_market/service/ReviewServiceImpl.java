package cs545_project.online_market.service;

import cs545_project.online_market.controller.response.ProductResponse;
import cs545_project.online_market.controller.response.ReviewResponse;
import cs545_project.online_market.domain.Review;
import cs545_project.online_market.domain.ReviewStatus;
import cs545_project.online_market.repository.ReviewRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author knguyen93
 */
@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {
    private ReviewRepository reviewRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<ReviewResponse> getCreatedReviews() {
        return this.reviewRepository.findReviewsByStatus(ReviewStatus.NEW)
            .orElseGet(ArrayList::new)
            .stream()
            .map(r -> {
                ReviewResponse review = new ReviewResponse();
                review.setReviewer(r.getReviewer().getFullName());
                review.setCreatedDate(r.getCreatedDate());
                review.setId(r.getId());
                review.setText(r.getText());

                ProductResponse product = new ProductResponse();
                product.setId(r.getProduct().getId());
                product.setName(r.getProduct().getName());
                product.setImage(r.getProduct().getImage());
                review.setProduct(product);
                return review;
            }).collect(Collectors.toList());
    }

    @Override
    public void postReview(long reviewId) throws NotFoundException {

    }

    @Override
    public void declineReview(long reviewId) throws NotFoundException {

    }
}
