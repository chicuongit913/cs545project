package cs545_project.online_market.controller.admin;

import cs545_project.online_market.service.ProductService;
import cs545_project.online_market.service.ReviewService;
import cs545_project.online_market.service.SellerService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private SellerService sellerService;
    private ReviewService reviewService;
    private ProductService productService;

    @Autowired
    public AdminController(SellerService sellerService, ReviewService reviewService, ProductService productService) {
        this.sellerService = sellerService;
        this.reviewService = reviewService;
        this.productService = productService;
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

    @GetMapping("/post-review/{reviewId}")
    public String postReview(@PathVariable("reviewId") long reviewId, HttpServletRequest request) throws NotFoundException {
        reviewService.postReview(reviewId);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @GetMapping("/decline-review/{reviewId}")
    public String declineReview(@PathVariable("reviewId") long reviewId, HttpServletRequest request) throws NotFoundException {
        reviewService.declineReview(reviewId);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @GetMapping("/products")
    public String showAllProducts(Model model) {
        model.addAttribute("products", productService.getAllUnUsedProducts());
        return "views/admin/products-management";
    }

    @GetMapping(value = "/delete-product/{productId}")
    public String deleteProduct(@PathVariable long productId, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            productService.deleteProductByAdmin(productId);
            redirectAttributes.addFlashAttribute("message", "Product has been deleted successfully");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("product_error", ex.getMessage());
        }
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }
}
