package cs545_project.online_market.service;

import cs545_project.online_market.domain.User;

import java.util.Optional;

public interface UserService {
    public Optional<User> findByUsername(String userName);
}
