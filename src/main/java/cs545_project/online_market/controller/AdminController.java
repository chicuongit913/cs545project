package cs545_project.online_market.controller;

import cs545_project.online_market.service.ReviewService;
import cs545_project.online_market.service.SellerService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    SellerService sellerService;
    @Autowired
    ReviewService reviewService;
    @GetMapping("/pending-sellers")
    public String pendingSellers(Model model){
        model.addAttribute("pendingSellers", sellerService.getPendingSellers());
        return "admin/pending-sellers";
    }
    @PutMapping("/approve-seller/{sellerId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void approveSeller(@PathVariable("sellerId") long sellerId) throws NotFoundException {
        sellerService.approveSeller(sellerId);
    }
    @PutMapping("/reject-seller/{sellerId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void rejectSeller(@PathVariable("sellerId") long sellerId) throws NotFoundException {
        sellerService.rejectSeller(sellerId);
    }


    @GetMapping("/created-reviews")
    public String createdReviews(Model model){
        model.addAttribute("createdReviews", reviewService.getCreatedReviews());
        return "admin/created-reviews";
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
