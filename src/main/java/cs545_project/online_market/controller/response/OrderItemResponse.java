package cs545_project.online_market.controller.response;

import lombok.Data;

import java.util.List;

@Data
public class OrderItemResponse {
    private String productName;
    private double price;
    private int quantity;
    private String image;
}
