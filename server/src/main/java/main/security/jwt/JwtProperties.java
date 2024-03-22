package main.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private String secretKey = "jwtSecretKey";
    private long isValidInMs = 1800000;

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public long getIsValidInMs() {
        return isValidInMs;
    }

    public void setIsValidInMs(long isValidInMs) {
        this.isValidInMs = isValidInMs;
    }
}
