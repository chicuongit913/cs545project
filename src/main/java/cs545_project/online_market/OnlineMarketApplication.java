package cs545_project.online_market;

import org.hashids.Hashids;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OnlineMarketApplication {

    @Bean
    public Hashids hashids() {
        return new Hashids("Order-is-generator");
    }

    public static void main(String[] args) {
        SpringApplication.run(OnlineMarketApplication.class, args);
    }
}
