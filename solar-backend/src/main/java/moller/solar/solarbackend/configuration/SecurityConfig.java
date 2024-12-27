package moller.solar.solarbackend.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

    @Value("${security.allowed-email-claim}")
    private String allowedEmailClaim;

    /**
     * Implementing OAuth2 authentication via JSON Web Tokens (JWT). Setting up the SecurityFilterChain with:
     * - CSRF: Not needed. "By using the oauth2ResourceServer() DSL, you are telling Spring Security that you are not
     * using cookie-based authentication, therefore you do not need CSRF protection."
     * Ref: https://stackoverflow.com/questions/71781409/spring-webflux-with-jwt-csrf-token/71782433#71782433
     * - CORS: May not be needed anymore, but was in previous versions of Spring
     * - Authorization: Deny all request by default while allowing specific path patterns
     * - The actuator health check is publicly accessible without authentication
     * - The Custom Controllers requires authentication and supports GET/POST/PUT requests only.
     * <p>
     * Example Authorization Usage:
     * curl -X GET -H "Authorization: Bearer {JWT}"  https://localhost:8443/api/v1/users/
     *
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(HttpMethod.GET,
                                "/actuator/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**").permitAll()
                        //
                        .requestMatchers(HttpMethod.GET, "/api/v1/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/v1/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/**").authenticated()
                        //
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/**").authenticated()
                        .anyRequest().denyAll()
                )
                .oauth2ResourceServer((oauth2) -> oauth2.jwt(withDefaults()));
        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        NimbusJwtDecoder jwtDecoder = JwtDecoders.fromIssuerLocation("https://accounts.google.com");

        OAuth2TokenValidator<Jwt> emailValidator = new DelegatingOAuth2TokenValidator<>(
                new JwtClaimValidator<>("email", claim -> claim.equals(allowedEmailClaim)));

        jwtDecoder.setJwtValidator(emailValidator);

        return jwtDecoder;
    }

    /**
     * Temporary override to better display and test the changes we are making to JWT Authentication and to validate
     * that our changes are producing the output we expect. This can be deleted in the future as it has no functional
     * purpose.
     *
     * @return - Prints a message to the console to manually validate authentication.
     */
    @Bean
    ApplicationListener<AuthenticationSuccessEvent> successfulEvent() {
        return event -> {
        };
    }

    @Bean
    ApplicationListener<AuthenticationFailureBadCredentialsEvent> failureBadCredentialsEvent() {
        return event -> {
            LOGGER.error("FailureBadCredentials: {}", String.valueOf(event.getException()));
            LOGGER.error("Event details: {}", event);
        };
    }
}
