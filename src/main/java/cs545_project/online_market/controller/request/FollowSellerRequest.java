package cs545_project.online_market.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author knguyen93
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowSellerRequest {

    @NotBlank
    private String buyerUserName;
    @NotBlank
    private String sellerUserName;
}
