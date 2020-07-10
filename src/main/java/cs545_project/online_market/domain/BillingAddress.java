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
@DiscriminatorValue("billing")
@ToString
public class BillingAddress extends Address {
    public BillingAddress(String street, String city, String state, int zipCode) {
        super(street, city, state, zipCode);
    }
}
