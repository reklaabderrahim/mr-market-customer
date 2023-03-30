package fr.mr_market.mr_customer.configuration;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;

@Getter
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private final Rsa rsa = new Rsa();

    @Data
    public static class Rsa {
        private RSAPublicKey publicKey;
    }
}
