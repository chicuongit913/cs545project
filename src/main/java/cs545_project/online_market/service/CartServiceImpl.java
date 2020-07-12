package cs545_project.online_market.service;

import cs545_project.online_market.controller.request.CartRequest;
import cs545_project.online_market.domain.Cart;
import cs545_project.online_market.domain.Product;
import cs545_project.online_market.repository.CartRepository;
import cs545_project.online_market.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    private CartRepository cartRepository;
    private ProductRepository productRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Cart create(Cart cart) {
        return cartRepository.create(cart);
    }

    @Override
    public Cart read(String cartId) {
        return cartRepository.read(cartId);
    }

    @Override
    public void update(String cartId, Cart cart) {
        cartRepository.update(cartId, cart);
    }

    @Override
    public void delete(String cartId) {
        cartRepository.delete(cartId);
    }

    @Override
    public Cart checkAndUpdateCart(String cartId, CartRequest cartRequest) {
    	Cart cart = cartRepository.read(cartId);
        cartRequest.getItems()
            .stream()
            .forEach(item -> {
                Product product = productRepository
                    .findById(item.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid Product"));
                if (product.getStock() < item.getQuantity()) {
                	throw new IllegalArgumentException(
                		String.format("We only has %d item(s) left for %s", product.getStock(), product.getName()));
				}

                cart.updateCartItem(product.getId(), item.getQuantity());
            });
        cartRepository.update(cart.getCartId(), cart);
        return cart;
    }


}