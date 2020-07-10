package cs545_project.online_market.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("shipping")
@ToString
public class ShippingAddress extends Address {
    public ShippingAddress(String street, String city, String state, int zipCode) {
        super(street, city, state, zipCode);
    }
}
