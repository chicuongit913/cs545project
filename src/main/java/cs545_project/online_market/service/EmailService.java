package cs545_project.online_market.service;

import cs545_project.online_market.controller.response.OrderResponse;
import cs545_project.online_market.domain.Order;

public interface EmailService {
    void sendConfirmPurchaseMessage(OrderResponse orderResponse);
}
