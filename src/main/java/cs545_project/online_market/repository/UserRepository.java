package cs545_project.online_market.repository;

import cs545_project.online_market.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByUsername(String username);
    public List<User> findByEmail(String email);
}
