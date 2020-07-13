package cs545_project.online_market.repository;

import cs545_project.online_market.domain.Address;
import cs545_project.online_market.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
