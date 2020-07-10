package cs545_project.online_market.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * @author knguyen93
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    @NotEmpty
    @Valid
    private List<ItemRequest> itemRequests;

    @NotNull
    private AddressRequest billingAddress;

    @NotNull
    private AddressRequest shippingAddress;

    private boolean applyCoupon;

    @Data
    public static class ItemRequest {

        @NotNull
        private Long productId;

        @NotNull
        @Positive
        private Integer quantity;
    }
}
