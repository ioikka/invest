package io.ikka.invest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class InvestApplication {

    public static void main(String[] args) {
        SpringApplication.run(InvestApplication.class, args);
    }

}
