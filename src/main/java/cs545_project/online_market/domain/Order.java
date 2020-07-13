package cs545_project.online_market.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "product_order")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.NEW;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private List<OrderDetails> orderDetails = new ArrayList<>();

    private double points;
    private double credit;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    private String receiver;

    @OneToOne
    @JoinColumn(name = "card_id")
    private Card card;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinTable(name = "order_shipping")
    private ShippingAddress shippingAddress;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinTable(name = "order_billing")
    private BillingAddress billingAddress;

    @ManyToOne
    @JoinTable(
            name = "buyer_order",
            joinColumns = {@JoinColumn(name = "order_id")},
            inverseJoinColumns = {@JoinColumn(name = "buyer_id")}
    )
    private User buyer;

    public boolean canCancel() {
        return OrderStatus.NEW.equals(status);
    }

    public double total() {
        return orderDetails.stream()
            .mapToDouble(OrderDetails::total)
            .sum();
    }
}

