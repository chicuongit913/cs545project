package cs545_project.online_market.controller.request;

import cs545_project.online_market.validation.ensureZipcode.EnsureZipcode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequest {

    private int id;

    @NotEmpty
    private String street;

    @NotEmpty
    private String city;

    @NotEmpty
    @Pattern(regexp = "[\\w]{2}", message = "State mus be 2 words!")
    private String state;

    @EnsureZipcode(message = "Zipcode must be 5 digits!")
    private int zipCode;
}
