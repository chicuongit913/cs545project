package cs545_project.online_market.repository;

import cs545_project.online_market.domain.Cart;

public interface CartRepository {

	Cart create(Cart cart);

	Cart read(String cartId);

	void update(String cartId, Cart cart);

	void delete(String cartId);

	void emptyCart(String cartId);
}
