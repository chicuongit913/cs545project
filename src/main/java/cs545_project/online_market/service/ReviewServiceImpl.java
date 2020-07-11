package cs545_project.online_market.service;

import cs545_project.online_market.domain.Review;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author knguyen93
 */
@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {
    @Override
    public List<Review> getCreatedReviews() {
        return null;
    }

    @Override
    public void postReview(long reviewId) throws NotFoundException {

    }

    @Override
    public void declineReview(long reviewId) throws NotFoundException {

    }
}
