package cs545_project.online_market.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int quantity;

    private double price;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.NEW;

    public OrderDetails(Product product, int quantity, double price) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public double total() {
        return status != OrderStatus.CANCELED ? price * quantity : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetails that = (OrderDetails) o;
        return id == that.id &&
            quantity == that.quantity &&
            Double.compare(that.price, price) == 0 &&
            Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantity, price, product);
    }

    public boolean canCancel() {
        return OrderStatus.NEW.equals(status);
    }
}
