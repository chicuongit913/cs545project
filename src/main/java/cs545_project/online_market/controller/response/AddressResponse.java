package cs545_project.online_market.controller.response;

import lombok.Data;

import javax.validation.constraints.Size;

/**
 * @author knguyen93
 */
@Data
public class AddressResponse {
    private Long id;
    private String street;
    private String city;
    private String state;
    private int zipCode;

    public String getBeautyAddress() {
        return String.format("%s, %s, %s %d", street, city, state, zipCode);
    }
}
