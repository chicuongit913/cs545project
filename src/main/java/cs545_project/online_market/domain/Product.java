package cs545_project.online_market.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String description;

    private double price;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
        name = "product_image",
        joinColumns = {@JoinColumn(name = "product_id")},
        inverseJoinColumns = {@JoinColumn(name = "image_id")}
    )
    private List<Image> images = new ArrayList<>();

    @ManyToOne
    @JoinTable(
        name = "product_seller",
        joinColumns = {@JoinColumn(name = "product_id")},
        inverseJoinColumns = {@JoinColumn(name = "seller_id")}
    )
    private User seller;

    private Integer stock;

    /**
     * Once Product is in use, it no longer be deleted
     */
    private boolean isInUse;

    @OneToMany
    @JoinTable(
        name = "product_review",
        joinColumns = {@JoinColumn(name = "product_id")},
        inverseJoinColumns = {@JoinColumn(name = "review_id")}
    )
    private List<Review> reviews = new ArrayList<>();

    @Temporal(value = TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdDate;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date updatedDate;

    public boolean canDelete() {
        return !isInUse;
    }

    public void addReview(Review review) {
        this.reviews.add(review);
    }

    public void addImage(Image image) {
        this.images.add(image);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id &&
            Objects.equals(name, product.name) &&
            Objects.equals(description, product.description) &&
            Objects.equals(seller, product.seller);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, seller);
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", price=" + price +
            ", stock=" + stock +
            ", isInUse=" + isInUse +
            ", reviews=" + reviews +
            '}';
    }
}