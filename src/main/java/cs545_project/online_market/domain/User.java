package cs545_project.online_market.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "email")
	private String email;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	//set status for user 0: in-active user - 1: active user
	@Column(name = "active")
	private int active;

	@Enumerated(EnumType.STRING)
	private UserRole role;

	@OneToMany(cascade = CascadeType.ALL)
	private List<ShippingAddress> shippingAddresses = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL)
	private List<BillingAddress> billingAddresses = new ArrayList<>();

	@OneToMany(mappedBy = "seller")
	private List<Product> products = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(
		name = "buyer_following",
		joinColumns = {@JoinColumn(name = "buyer_id")},
		inverseJoinColumns = {@JoinColumn(name = "seller_id")}
	)
	private List<User> followingSellers = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(
		name = "buyer_order",
		joinColumns = {@JoinColumn(name = "buyer_id")},
		inverseJoinColumns = {@JoinColumn(name = "order_id")}
	)
	private List<Order> orders = new ArrayList<>();

	/**
	 * Store Buyer points. This points will be updated every time Buyer make/cancel/return Order
	 */
	@Column(nullable = true, columnDefinition="int(1) default '0'")
	private double points;

	public void addProduct(Product product) {
		this.products.add(product);
	}

	public void addOrder(Order order) {
		this.orders.add(order);
	}

	public void removeProduct(Product product) {
		this.products.remove(product);
	}

	public void addShippingAddress(ShippingAddress address) {
		this.shippingAddresses.add(address);
	}

	public void addBillingAddress(BillingAddress address) {
		this.billingAddresses.add(address);
	}

	public void followSeller(User seller) {
		this.followingSellers.add(seller);
	}

	public void unFollowSeller(User seller) {
		this.followingSellers.remove(seller);
	}

	@Override
	public String toString() {
		return "User{" +
			"id=" + id +
			", firstName='" + firstName + '\'' +
			", lastName='" + lastName + '\'' +
			", email='" + email + '\'' +
			", username='" + username + '\'' +
			", password='" + password + '\'' +
			", active=" + active +
			", role=" + role +
			", shippingAddresses=" + shippingAddresses +
			", billingAddresses=" + billingAddresses +
			'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return id == user.id &&
			active == user.active &&
			Objects.equals(firstName, user.firstName) &&
			Objects.equals(lastName, user.lastName) &&
			Objects.equals(email, user.email) &&
			Objects.equals(username, user.username) &&
			Objects.equals(password, user.password) &&
			role == user.role;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, firstName, lastName, email, username, password, active, role);
	}
}

