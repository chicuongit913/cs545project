package cs545_project.online_market.controller.admin;

import cs545_project.online_market.service.ReviewService;
import cs545_project.online_market.service.SellerService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private SellerService sellerService;
    private ReviewService reviewService;

    @Autowired
    public AdminController(SellerService sellerService, ReviewService reviewService) {
        this.sellerService = sellerService;
        this.reviewService = reviewService;
    }

    @GetMapping("/pending-sellers")
    public String pendingSellers(Model model) {
        model.addAttribute("pendingSellers", sellerService.getPendingSellers());
        return "views/admin/pending_sellers";
    }

    @GetMapping("/approve-seller/{sellerId}")
    public String approveSeller(@PathVariable("sellerId") long sellerId, HttpServletRequest request) throws NotFoundException {
        sellerService.approveSeller(sellerId);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @GetMapping("/reject-seller/{sellerId}")
    public String rejectSeller(@PathVariable("sellerId") long sellerId, HttpServletRequest request) throws NotFoundException {
        sellerService.rejectSeller(sellerId);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }


    @GetMapping("/pending-reviews")
    public String createdReviews(Model model) {
        model.addAttribute("createdReviews", reviewService.getCreatedReviews());
        return "views/admin/created_reviews";
    }

    @PutMapping("/post-review/{reviewId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void postReview(@PathVariable("reviewId") long reviewId) throws NotFoundException {
        reviewService.postReview(reviewId);
    }

    @PutMapping("/decline-review/{reviewId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void declineReview(@PathVariable("reviewId") long reviewId) throws NotFoundException {
        reviewService.declineReview(reviewId);
    }
}
