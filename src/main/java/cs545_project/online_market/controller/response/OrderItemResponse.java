package cs545_project.online_market.controller.response;

import cs545_project.online_market.domain.OrderStatus;
import lombok.Data;

import java.util.List;

@Data
public class OrderItemResponse {
    private long id;
    private Long productId;
    private String productName;
    private double price;
    private int quantity;
    private String image;
    private OrderStatus status;
}
