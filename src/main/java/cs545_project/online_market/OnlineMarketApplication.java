package cs545_project.online_market;

import org.hashids.Hashids;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
public class OnlineMarketApplication {

    @Bean
    public Hashids hashids() {
        return new Hashids("Order-is-generator", 8);
    }

    @Bean
    public void init() throws IOException {
        Files.createDirectories(Paths.get(System.getProperty("user.dir") + "/images/"));
    }

    public static void main(String[] args) {
        SpringApplication.run(OnlineMarketApplication.class, args);
    }
}
