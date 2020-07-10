package cs545_project.online_market.service;

import javassist.NotFoundException;

import java.util.List;

public interface SellerService<Seller> {
    List<Seller> getPendingSellers();
    void approveSeller(long sellerId) throws NotFoundException;
    void rejectSeller(long sellerId) throws NotFoundException;
    // ShoppingCart getSellerByProductId(Buyer buyer, long sellerId) throws NotFoundException;
    Seller getSellerById(long sellerId) throws NotFoundException;
}