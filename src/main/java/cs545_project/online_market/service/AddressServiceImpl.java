package cs545_project.online_market.service;

import cs545_project.online_market.controller.request.AddressRequest;
import cs545_project.online_market.domain.Address;
import cs545_project.online_market.domain.BillingAddress;
import cs545_project.online_market.domain.ShippingAddress;
import cs545_project.online_market.domain.User;
import cs545_project.online_market.repository.AddressRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressRepository addressRepository;

    @Override
    public Address findById(int id) {
        return this.addressRepository.findById(id).isPresent()?
                this.addressRepository.findById(id).get():null;
    }

    @Override
    public void delete(Address address) {
        this.addressRepository.delete(address);
    }

    @Override
    public Address save(Address address) {
        return this.addressRepository.save(address);
    }

    @Override
    public Address createOrUpdate(AddressRequest addressRequest, String type, User user) {
        Address address;
        if(type.equals("billing"))
            address = new BillingAddress();
        else
            address = new ShippingAddress();

        BeanUtils.copyProperties(addressRequest, address);
        address.setUser(user);
        return this.save(address);
    }
}
