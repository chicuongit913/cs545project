package cs545_project.online_market.repository;

import cs545_project.online_market.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    List<User> findByEmail(String email);

    @Query("select u from User u where u.role = 'SELLER' and u.sellerStatus = 'NEW'")
    List<User> findAllPendingSellers();

    @Query("select u from User u where u.role = 'SELLER' and u.id = :id")
    Optional<User> findSellerById(Long id);
}
