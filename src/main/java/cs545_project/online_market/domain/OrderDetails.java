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

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

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
}
