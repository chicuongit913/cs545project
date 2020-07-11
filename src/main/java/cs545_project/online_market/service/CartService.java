package cs545_project.online_market.service;

import cs545_project.online_market.domain.Cart;

public interface CartService {
	Cart create(Cart cart);

	Cart read(String cartId);

	void update(String cartId, Cart cart);

	void delete(String cartId);
}
