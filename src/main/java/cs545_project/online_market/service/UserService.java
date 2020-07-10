package cs545_project.online_market.service;

import cs545_project.online_market.controller.request.AddressRequest;
import cs545_project.online_market.controller.request.FollowSellerRequest;
import cs545_project.online_market.controller.request.UserRequest;
import cs545_project.online_market.domain.User;
import cs545_project.online_market.domain.UserRole;
import cs545_project.online_market.validation.uniqueKey.FieldValueExists;

import java.util.Optional;

public interface UserService extends FieldValueExists{
    Optional<User> findByUsername(String userName);
    User followSeller(FollowSellerRequest request);
    User unFollowSeller(FollowSellerRequest request);
    User addShippingAddress(String buyerUserName, AddressRequest request);
    User addBillingAddress(String buyerUserName, AddressRequest request);
    public User createUser(UserRequest user, UserRole userRole, int active);
    public User createSeller(UserRequest userRequest);
    public User createBuyer(UserRequest userRequest);
}
