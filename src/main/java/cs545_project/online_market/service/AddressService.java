package cs545_project.online_market.service;

import cs545_project.online_market.controller.request.AddressRequest;
import cs545_project.online_market.domain.Address;
import cs545_project.online_market.domain.User;

public interface AddressService {
    public Address findById(int id);
    public void delete(Address address);
    public Address save(Address address);
    public Address createOrUpdate(AddressRequest addressRequest, String type, User user);
}
