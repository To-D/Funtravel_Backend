package pers.jaxon.funtravel.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Some properties about JWT.
 */
@Component
@ConfigurationProperties(prefix = "jwt.token")
public class JwtConfigProperties {
    private int validity;
    private String secret;

    public int getValidity() {
        return validity;
    }

    public void setValidity(int validity) {
        this.validity = validity;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
