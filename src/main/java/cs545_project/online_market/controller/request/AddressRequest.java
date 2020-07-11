package cs545_project.online_market.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author knguyen93
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequest {

    private Long id;

    @NotBlank
    private String street;

    @NotBlank
    private String city;

    @NotBlank
    @Size(min = 2, max = 2, message = "{Size.state}")
    private String state;

    @Min(11111)
    @Max(99999)
    private int zipCode;
}
