package fr.mr_market.mr_customer;

import fr.mr_market.mr_customer.configuration.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({AppProperties.class})
public class MrMarketCustomerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MrMarketCustomerApplication.class, args);
    }

}
