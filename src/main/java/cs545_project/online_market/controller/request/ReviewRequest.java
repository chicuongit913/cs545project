package cs545_project.online_market.controller.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author knguyen93
 */
@Data
public class ReviewRequest {
    @NotBlank
    private String review;
}
