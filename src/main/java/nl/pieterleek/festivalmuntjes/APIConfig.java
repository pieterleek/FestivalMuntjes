package nl.pieterleek.festivalmuntjes;

import nl.pieterleek.festivalmuntjes.notifications.AnnouncementDistributor;
import nl.pieterleek.festivalmuntjes.notifications.NotificationDistributor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import java.net.Inet4Address;
import java.net.UnknownHostException;

//@EnableWebMvc  -- has a side effect of changing JSON LocalDate format, also impacts cors on unmapped paths
@Configuration
public class APIConfig implements WebMvcConfigurer, WebSocketConfigurer {


    @Autowired
    private AnnouncementDistributor announcementDistributor;

    @Autowired
    private NotificationDistributor notificationDistributor;

    @Value("${allowed.cors.clients:http://*.festivalmuntjes.com:*, http://localhost:*}")
    private String allowedCorsClients;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(this.announcementDistributor, "/announcements")
                .setAllowedOriginPatterns("http://localhost:*", getHostIPAddressPattern(), allowedCorsClients)
        //.withSockJS()
        ;
        registry.addHandler(this.notificationDistributor, "/notifications")
                .setAllowedOriginPatterns("http://localhost:*", getHostIPAddressPattern(), allowedCorsClients)
        //.withSockJS()
        ;
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
}
