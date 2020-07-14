package cs545_project.online_market.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @author Eman
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    @NotBlank
    @Size(min = 3, max = 50, message = "{Size.validation}")
    private String name;

    @NotBlank
    @Size(min = 10, max = 200, message = "{Size.validation}")
    private String description;

    private double price;

    private MultipartFile image;

    private Integer stock;

    private Long id;
}
