package cs545_project.online_market.controller.response;

import cs545_project.online_market.domain.User;
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

    private List<ReviewResponse> reviews = new ArrayList<>();

    private User seller;
}