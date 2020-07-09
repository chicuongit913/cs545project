package cs545_project.online_market.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("shipping")
public class ShippingAddress extends Address {
    public ShippingAddress(String street,  String city, String state, String zipCode, User user) {
        super(street,city,state,zipCode,user);
    }
}
