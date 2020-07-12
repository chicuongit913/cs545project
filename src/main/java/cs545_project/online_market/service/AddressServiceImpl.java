package cs545_project.online_market.service;

import cs545_project.online_market.domain.Address;
import cs545_project.online_market.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressRepository addressRepository;

    @Override
    public Address save(Address address) {
        return this.addressRepository.save(address);
    }
}
