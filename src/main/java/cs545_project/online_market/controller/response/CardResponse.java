package cs545_project.online_market.controller.response;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author knguyen93
 */
@Data
public class CardResponse {
    private Long id;
    private String name;
    private String cardNumber;
    private LocalDate valid;
}
