package cs545_project.online_market.domain;

/**
 * @author knguyen93
 */
public enum OrderStatus {
    NEW, // only be canceled in this status
    SHIPPED,
    DELIVERING,
    DELIVERED,
    CANCELED
}
