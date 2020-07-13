package cs545_project.online_market.controller.response;

import cs545_project.online_market.domain.OrderDetails;
import cs545_project.online_market.domain.OrderStatus;
import cs545_project.online_market.domain.User;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author knguyen93
 */
@Data
public class OrderResponse {
    private String orderCode;
    private long id;
    private OrderStatus status;
    private double total;
    private double credit;
    private double points;
    private Date createdDate;
    private String receiver;
    private double earnedPoints;
    private AddressResponse shippingAddress;
    private AddressResponse billingAddress;
    private List<OrderItemResponse> orderItems;
    private User buyer;
    private String CardNumber;
}
