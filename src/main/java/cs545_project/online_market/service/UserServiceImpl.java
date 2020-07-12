package cs545_project.online_market.service;

import cs545_project.online_market.controller.request.AddressRequest;
import cs545_project.online_market.controller.request.FollowSellerRequest;
import cs545_project.online_market.controller.request.UserRequest;
import cs545_project.online_market.controller.response.AddressResponse;
import cs545_project.online_market.domain.BillingAddress;
import cs545_project.online_market.domain.ShippingAddress;
import cs545_project.online_market.domain.User;
import cs545_project.online_market.domain.UserRole;
import cs545_project.online_market.repository.UserRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@NoArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    @Override
    public User createSeller(UserRequest userRequest) {
        return this.createUser(userRequest, UserRole.SELLER, 0);
    }

    @Override
    public User createBuyer(UserRequest userRequest) {
        return this.createUser(userRequest, UserRole.BUYER, 1);
    }

    @Override
    public User createUser(UserRequest userRequest, UserRole userRole, int active) {
        User user = new User();

        BeanUtils.copyProperties(userRequest, user);

        // Encode password string to BCryptPasswordEncoder
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Set Role seller when user register is seller
        user.setRole(userRole);
        user.setActive(active);

        return this.userRepository.save(user);
    }

    @Override
    public boolean fieldValueExists(Object value, String fieldName) throws UnsupportedOperationException {

        if (value == null) {
            return false;
        }

        if(fieldName.equals("email")) {
            return this.userRepository.findByEmail(value.toString()).size() != 0;
        }

        if(fieldName.equals("username")) {
            return this.userRepository.findByUsername(value.toString()).isPresent();
        }

        return false;
    }
}
