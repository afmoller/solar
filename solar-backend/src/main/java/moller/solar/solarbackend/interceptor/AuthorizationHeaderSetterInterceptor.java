package moller.solar.solarbackend.interceptor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.io.IOException;

public class AuthorizationHeaderSetterInterceptor  implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        String authorizationHeader = getToken();
        if (authorizationHeader != null) {
            request.getHeaders().add(HttpHeaders.AUTHORIZATION, OAuth2AccessToken.TokenType.BEARER.getValue() + " " + authorizationHeader);
        }
        return execution.execute(request, body);
    }

    private String getToken() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context != null && context.getAuthentication() instanceof JwtAuthenticationToken jwtAuthenticationToken) {
            return jwtAuthenticationToken.getToken().getTokenValue();
        } else {
            throw new IllegalStateException("SecurityContext is null or the Authentication is not an instance of JwtAuthenticationToken");
        }
    }
}
