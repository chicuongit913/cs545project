package cs545_project.online_market.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private long id;

    private String name;

    private String description;

    private double price;

    private String image;

    public String getImage() {
        return image != null && image.startsWith("http")
            ? image
            : String.format("/product_images/%s.png", image);
    }

    private boolean isInUse;

    private List<ReviewResponse> reviews = new ArrayList<>();

    private SellerResponse seller;
}