package cs545_project.online_market.service;

import cs545_project.online_market.domain.User;
import cs545_project.online_market.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> findByUsername(String userName) {
        return this.userRepository.findByUsername(userName);
    }
}
