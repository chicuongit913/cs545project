package cs545_project.online_market.controller.response;

import cs545_project.online_market.domain.OrderDetails;
import lombok.Data;

import java.util.List;

/**
 * @author knguyen93
 */
@Data
public class OrderResponse {
    private String orderCode;
    private double total;
    private double credit;
    private double points;
    private AddressResponse shippingAddress;
    private AddressResponse billingAddress;
    private List<OrderItemResponse> orderItems;
}
