package cs545_project.online_market.controller.request;

import cs545_project.online_market.domain.Review;
import cs545_project.online_market.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Eman
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {


    @NotEmpty
    @Size(min = 3, max = 50, message = "{Size.validation}")
    private String name;

    @NotEmpty
    @Size(min = 10, max = 200, message = "{Size.validation}")
    private String description;

    private double price;

    private MultipartFile image;

    private User seller;

    private Integer stock;

    private boolean isInUse;

    private List<Review> reviews = new ArrayList<>();

    @CreationTimestamp
    private Date createdDate;

    private Date updatedDate;
}
