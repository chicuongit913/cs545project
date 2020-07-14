package cs545_project.online_market.service;

import cs545_project.online_market.controller.response.SellerResponse;
import java.util.List;

public interface SellerService {
    List<SellerResponse> getPendingSellers();
    void approveSeller(long sellerId);
    void rejectSeller(long sellerId);
    SellerResponse getSellerById(long sellerId);
}