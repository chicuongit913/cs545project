package cs545_project.online_market.service;

import cs545_project.online_market.controller.request.FollowSellerRequest;
import cs545_project.online_market.domain.User;
import cs545_project.online_market.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
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
    public void followSeller(FollowSellerRequest request) {
        User buyer = this.userRepository.findByUsername(request.getBuyerUserName())
            .map(b -> {
                b.followSeller(
                    this.userRepository.findByUsername(request.getSellerUserName())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid Seller")));
                return b;
            })
            .orElseThrow(() -> new IllegalArgumentException("Invalid Buyer"));

        this.userRepository.save(buyer);
    }

    @Override
    public void unFollowSeller(FollowSellerRequest request) {
        User buyer = this.userRepository.findByUsername(request.getBuyerUserName())
            .map(b -> {
                b.unFollowSeller(
                    this.userRepository.findByUsername(request.getSellerUserName())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid Seller")));
                return b;
            })
            .orElseThrow(() -> new IllegalArgumentException("Invalid Buyer"));

        this.userRepository.save(buyer);
    }
}
