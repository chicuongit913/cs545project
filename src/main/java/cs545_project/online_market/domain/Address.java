package cs545_project.online_market.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorColumn(name = "address_type", discriminatorType = DiscriminatorType.STRING)
public class Address {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;

	@NotEmpty(message = "{String.empty}")
	private String street;
	private String city;

	@Size(min = 2, max = 2, message = "{Size.state}")
	private String state;

	private String zipCode;

	@ManyToOne()
	@JoinColumn(name = "user_id")
	private User user;

	public Address(String street, String city, String state, String zipCode, User user) {
		this.street = street;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.user = user;
	}
}
