package cs545_project.online_market.repository;

import cs545_project.online_market.domain.Review;
import cs545_project.online_market.domain.ReviewStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author knguyen93
 */
@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {
    Optional<List<Review>> findReviewsByStatus(ReviewStatus status);
}
