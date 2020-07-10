package cs545_project.online_market.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "address_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Address {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;

	@NotEmpty(message = "{String.empty}")
	private String street;
	private String city;

	@Size(min = 2, max = 2, message = "{Size.state}")
	private String state;

	private int zipCode;

	public Address(String street, String city, String state, int zipCode) {
		this.street = street;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
	}
}
