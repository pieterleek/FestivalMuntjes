package io.qman.festivalcoins.util;

import io.qman.festivalcoins.APIConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * This class is a filter that checks for a JSON Web Token (JWT) in the request.
 * It is annotated as a Component, meaning it's a Spring-managed bean.
 */

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    APIConfig apiConfig;

    @Autowired
    public JWTRequestFilter(APIConfig apiConfig) {
        this.apiConfig = apiConfig;
    }

    @Override
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain) throws jakarta.servlet.ServletException, IOException {

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
            response.sendError(jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED, "No token provided. You need to logon first.");
            return;
        }

        // decode the encoded and signed token, after removing optional Bearer prefix
        JWToken jwToken = null;
        try {
            jwToken = JWToken.decode(encryptedToken.replace("Bearer ", ""),
                    this.apiConfig.getIssuer(), this.apiConfig.getPassphrase());
        } catch (RuntimeException e) {
            response.sendError(jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED, e.getMessage() + " You need to logon first.");
            return;
        }

        // obtain the source ip address of the request
        String sourceIpAddress = JWToken.getIpAddress(request);

        // block the request if the source ip cannot be identified
        if (sourceIpAddress == null
                || !sourceIpAddress.equals(jwToken.getIpAddress())
        ) {
            response.sendError(jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED,
                    "Cannot identify or validate your source IP-Address.");
            return;
        }

        // pass-on the token info as an attribute for the request
        request.setAttribute(JWToken.JWT_ATTRIBUTE_NAME, jwToken);

        filterChain.doFilter(request, response);
    }
}
