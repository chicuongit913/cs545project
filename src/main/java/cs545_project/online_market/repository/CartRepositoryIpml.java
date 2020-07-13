package cs545_project.online_market.repository;

import cs545_project.online_market.domain.Cart;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class CartRepositoryIpml implements CartRepository {
	private Map<String, Cart> listOfCarts;

	public CartRepositoryIpml() {
		listOfCarts = new HashMap<String, Cart>();
	}

	@Override
	public Cart create(Cart cart) {
		if (listOfCarts.keySet().contains(cart.getCartId())) {
			throw new IllegalArgumentException(String
					.format("Can not create a cart. A cart with the give id (%) aldrady exist", cart.getCartId()));
		}
		listOfCarts.put(cart.getCartId(), cart);
		return cart;
	}

	@Override
	public Cart read(String cartId) {
		return listOfCarts.get(cartId);
	}

	@Override
	public void update(String cartId, Cart cart) {
		if (!listOfCarts.keySet().contains(cartId)) {
			throw new IllegalArgumentException(String
					.format("Can not update cart. The cart with the give id (%) does not does not exist", cartId));
		}
		listOfCarts.put(cartId, cart);
	}

	@Override
	public void delete(String cartId) {
		if (!listOfCarts.keySet().contains(cartId)) {
			throw new IllegalArgumentException(String
					.format("Can not delete cart. The cart with the give id (%) does not does not exist", cartId));
		}
		listOfCarts.remove(cartId);
	}

	@Override
	public void emptyCart(String cartId) {
		Optional.ofNullable(listOfCarts.get(cartId)).orElseThrow(() -> new IllegalArgumentException("Cart does not " +
			"exist")).empty();
	}
}