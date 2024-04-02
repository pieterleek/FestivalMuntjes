package io.qman.festivalcoins;

import io.qman.festivalcoins.notifications.NotificationDistributor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Set;

@Configuration
@EnableWebSocket
public class APIConfig implements WebMvcConfigurer, WebSocketConfigurer {

    public static final String IP_FORWARDED_FOR = "X-Forwarded-For";
    // a variable reboot signature can be used as an additional security layer in authentication tokens.
    private static final double REBOOT_CODE = 63.0427; // Math.random();
    // path prefixes that will be protected by the authentication filter
    public Set<String> SECURED_PATHS = Set.of("/accounts");
    @Autowired
    private NotificationDistributor notificationDistributor;
    // JWT configuration that can be adjusted from application.properties
    @Value("${jwt.issuer:private company}")
    private String issuer;

    @Value("${allowed.cors.clients:https://*.festivalmuntjes.com:*, http://localhost:*}")
    private String allowedCorsClients;

    @Value("${jwt.passphrase:This is very secret information for my private encryption key.}")
    private String passphrase;

    @Value("${jwt.duration-of-validity:1200}") // default 20 minutes;
    private int tokenDurationOfValidity;


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(notificationDistributor, "/websocket")
                .setAllowedOrigins("*");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("http://localhost:*", getHostIPAddressPattern(), "http://*.festivalmuntjes.com:*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedOrigins("*")
        ;
    }

    private String getHostIPAddressPattern() {
        try {
            return "http://" + Inet4Address.getLocalHost().getHostAddress() + ":*";
        } catch (UnknownHostException ignored) {
        }
        return "http://192.168.*.*:*";
    }

    public String getIssuer() {
        // include a reboot sequence nr in the issuer signature
        //  such that authentication tokens can be revoked after a reboot.
        return String.format("%s-%f", this.issuer, REBOOT_CODE);
    }


    public String getPassphrase() {
        return passphrase;
    }

    public int getTokenDurationOfValidity() {
        return tokenDurationOfValidity;
    }
}
