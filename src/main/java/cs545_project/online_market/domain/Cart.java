package cs545_project.online_market.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Cart {
	private String cartId;
	private Map<Long, CartItem> cartItems;
	private double grandTotal;
	private int quantity = 0;

	public Cart() {
		cartItems = new HashMap<>();
	}

	public Cart(String cartId) {
		this();
		this.cartId = cartId;
	}

	public void addCartItem(CartItem item) {
		long productId = Math.toIntExact(item.getProduct().getId());
		if (cartItems.containsKey(productId)) {
			CartItem existingCartItem = cartItems.get(productId);
			existingCartItem.setQuantity(existingCartItem.getQuantity() + item.getQuantity());
			cartItems.put(productId, existingCartItem);
		} else {
			cartItems.put(productId, item);
		}
		updateGrandTotal();
	}

	public void removeCartItem(CartItem item) {
		long productId = item.getProduct().getId();
		cartItems.remove(productId);
		updateGrandTotal();
	}

	public void updateCartItem(long productId, int quantity) {
		CartItem item = cartItems.get(productId);
		item.setQuantity(quantity);
		updateGrandTotal();
	}

	public void updateGrandTotal() {
		grandTotal = 0;
		quantity = 0;
		for (CartItem item : cartItems.values()) {
			grandTotal += item.getTotalPrice();
			quantity += item.getQuantity();
		}
		BigDecimal bd = new BigDecimal(grandTotal).setScale(2, RoundingMode.HALF_UP);
		grandTotal = bd.doubleValue();
	}

	@Override
	public int hashCode() {
		final int prime = 71;
		int result = 1;
		result = prime * result + ((cartId == null) ? 0 : cartId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cart other = (Cart) obj;
		if (cartId == null) {
			if (other.cartId != null)
				return false;
		} else if (!cartId.equals(other.cartId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cart [cartId=" + cartId + ", cartItems=" + cartItems + ", grandTotal=" + grandTotal + "]";
	}

    public void empty() {
		cartItems = new HashMap<>();
		grandTotal = 0;
		quantity = 0;
    }
}