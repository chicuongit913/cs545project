package cs545_project.online_market.controller.response;

import lombok.Data;

/**
 * @author knguyen93
 */
@Data
public class SellerResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
