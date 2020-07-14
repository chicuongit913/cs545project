package cs545_project.online_market.service;

import cs545_project.online_market.controller.response.SellerResponse;
import cs545_project.online_market.domain.SellerStatus;
import cs545_project.online_market.domain.User;
import cs545_project.online_market.domain.UserRole;
import cs545_project.online_market.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author knguyen93
 */
@Service
@Transactional
public class SellerServiceImpl implements SellerService {
    private UserRepository userRepository;

    @Autowired
    public SellerServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<SellerResponse> getPendingSellers() {
        return userRepository.findAllPendingSellers()
            .stream()
            .map(this::apply)
            .collect(Collectors.toList());
    }

    @Override
    public void approveSeller(long sellerId) {
        User seller = userRepository.findById(sellerId)
            .filter(u -> u.getRole() == UserRole.SELLER)
            .orElseThrow(() -> new IllegalArgumentException("Invalid Seller Id"));
        seller.setActive(1);
        seller.setSellerStatus(SellerStatus.APPROVED);
        userRepository.save(seller);
    }

    @Override
    public void rejectSeller(long sellerId) {
        User seller = userRepository.findById(sellerId)
            .filter(u -> u.getRole() == UserRole.SELLER)
            .orElseThrow(() -> new IllegalArgumentException("Invalid Seller Id"));
        seller.setActive(0);
        seller.setSellerStatus(SellerStatus.REJECTED);
        userRepository.save(seller);
    }

    @Override
    public SellerResponse getSellerById(long sellerId) {
        return userRepository.findSellerById(sellerId)
            .map(this::apply)
            .orElseThrow(() -> new IllegalArgumentException("Invalid Seller Id"));
    }

    private SellerResponse apply(User u) {
        SellerResponse seller = new SellerResponse();
        BeanUtils.copyProperties(u, seller);
        return seller;
    }
}
