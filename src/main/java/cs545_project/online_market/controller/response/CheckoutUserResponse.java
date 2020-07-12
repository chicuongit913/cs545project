package cs545_project.online_market.controller.response;

import cs545_project.online_market.domain.Card;
import lombok.Data;

import java.util.List;

/**
 * @author knguyen93
 */
@Data
public class CheckoutUserResponse {
    private String name;
    private String email;
    private List<AddressResponse> billingAddresses;
    private List<AddressResponse> shippingAddresses;
    private List<CardResponse> cards;
}
