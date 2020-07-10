package cs545_project.online_market.service;

import cs545_project.online_market.controller.request.AddressRequest;
import cs545_project.online_market.controller.request.FollowSellerRequest;
import cs545_project.online_market.domain.BillingAddress;
import cs545_project.online_market.domain.ShippingAddress;
import cs545_project.online_market.domain.User;
import cs545_project.online_market.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findByUsername(String userName) {
        return this.userRepository.findByUsername(userName);
    }

    @Override
    public User followSeller(FollowSellerRequest request) {
        User buyer = this.userRepository.findByUsername(request.getBuyerUserName())
            .map(b -> {
                b.followSeller(
                    this.userRepository.findByUsername(request.getSellerUserName())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid Seller")));
                return b;
            })
            .orElseThrow(() -> new IllegalArgumentException("Invalid Buyer"));

        return this.userRepository.save(buyer);
    }

    @Override
    public User unFollowSeller(FollowSellerRequest request) {
        User buyer = this.userRepository.findByUsername(request.getBuyerUserName())
            .map(b -> {
                b.unFollowSeller(
                    this.userRepository.findByUsername(request.getSellerUserName())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid Seller")));
                return b;
            })
            .orElseThrow(() -> new IllegalArgumentException("Invalid Buyer"));

        return this.userRepository.save(buyer);
    }

    @Override
    public User addBillingAddress(String buyerUserName, AddressRequest request) {
        User buyer = this.userRepository.findByUsername(buyerUserName)
            .map(b -> {
                b.addBillingAddress(mapToBillingAddress(request));
                return b;
            })
            .orElseThrow(() -> new IllegalArgumentException("Invalid Buyer"));

        return this.userRepository.save(buyer);
    }

    @Override
    public User addShippingAddress(String buyerUserName, AddressRequest request) {
        User buyer = this.userRepository.findByUsername(buyerUserName)
            .map(b -> {
                b.addShippingAddress(mapToShippingAddress(request));
                return b;
            })
            .orElseThrow(() -> new IllegalArgumentException("Invalid Buyer"));

        return this.userRepository.save(buyer);
    }

    private BillingAddress mapToBillingAddress(AddressRequest request) {
        BillingAddress billingAddress = new BillingAddress();
        BeanUtils.copyProperties(request, billingAddress, "id");
        return billingAddress;
    }

    private ShippingAddress mapToShippingAddress(AddressRequest request) {
        ShippingAddress shippingAddress = new ShippingAddress();
        BeanUtils.copyProperties(request, shippingAddress, "id");
        return shippingAddress;
    }
}
