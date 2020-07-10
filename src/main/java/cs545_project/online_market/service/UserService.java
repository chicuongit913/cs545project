package cs545_project.online_market.service;

import cs545_project.online_market.controller.request.FollowSellerRequest;
import cs545_project.online_market.domain.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String userName);
    void followSeller(FollowSellerRequest request);
    void unFollowSeller(FollowSellerRequest request);
}
