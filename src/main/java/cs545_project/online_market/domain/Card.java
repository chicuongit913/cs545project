package cs545_project.online_market.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String name;
    private String cardNumber;
    private int CVV;
    private LocalDate valid;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
