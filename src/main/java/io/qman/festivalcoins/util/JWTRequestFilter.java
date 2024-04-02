package io.qman.festivalcoins.util;

import io.qman.festivalcoins.APIConfig;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * This class is a filter that checks for a JSON Web Token (JWT) in the request.
 * It is annotated as a Component, meaning it's a Spring-managed bean.
 */
@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    // Configuration for the API
    APIConfig apiConfig;

    /**
     * Constructor for the JWTRequestFilter class.
     * @param apiConfig an instance of APIConfig
     */
    @Autowired
    public JWTRequestFilter(APIConfig apiConfig) {
        this.apiConfig = apiConfig;
    }

    /**
     * This method is called for every request. It checks if the request contains a valid JWT.
     * If the request does not contain a valid JWT, it sends an error response.
     * @param request the HttpServletRequest
     * @param response the HttpServletResponse
     * @param filterChain the FilterChain
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String servletPath = request.getServletPath();

        // OPTIONS requests and non-secured area should pass through without check
        if (HttpMethod.OPTIONS.matches(request.getMethod()) ||
                this.apiConfig.SECURED_PATHS.stream().noneMatch(servletPath::startsWith)) {
            filterChain.doFilter(request, response);
            return;
        }

        // get the encrypted token string from the authorization request header
        String encryptedToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        // block the request if no token was found
        if (encryptedToken == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No token provided. You need to logon first.");
            return;
        }

        // decode the encoded and signed token, after removing optional Bearer prefix
        JWToken jwToken = null;
        try {
            jwToken = JWToken.decode(encryptedToken.replace("Bearer ", ""),
                    this.apiConfig.getIssuer(), this.apiConfig.getPassphrase());
        } catch (RuntimeException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage() + " You need to logon first.");
            return;
        }
    }
}