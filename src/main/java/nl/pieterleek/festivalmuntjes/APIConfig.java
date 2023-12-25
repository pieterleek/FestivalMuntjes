package nl.pieterleek.festivalmuntjes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.net.Inet4Address;
import java.net.UnknownHostException;

//@EnableWebMvc  -- has a side effect of changing JSON LocalDate format, also impacts cors on unmapped paths
@Configuration
public class APIConfig implements WebMvcConfigurer {

    @Value("${repository.type:INMEMORY}")
    public String repositoryType;

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
