package cs545_project.online_market.domain;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Cart {
	private String cartId;
	private Map<Long, CartItem> cartItems;
	private double grandTotal;

	public Cart() {
		cartItems = new HashMap<Long, CartItem>();
	}

	public Cart(String cartId) {
		this();
		this.cartId = cartId;
	}

	public String getCartId() {
		return cartId;
	}

	public void setCartId(String cartId) {
		this.cartId = cartId;
	}

	public Map<Long, CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(Map<Long, CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	public double getGrandTotal() {
		return grandTotal;
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

	public void updateGrandTotal() {
		for (CartItem item : cartItems.values()) {
			grandTotal = item.getTotalPrice() * item.getQuantity();
		}
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
	
}