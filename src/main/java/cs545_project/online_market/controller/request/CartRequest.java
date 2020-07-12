package cs545_project.online_market.controller.request;

import cs545_project.online_market.domain.CartItem;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author knguyen93
 */
@Data
public class CartRequest {
    private List<CartItemRequest> items = new ArrayList<>();

    @Data
    public static class CartItemRequest {
        private Long id;
        private String name;
        private Integer quantity;
    }
}
