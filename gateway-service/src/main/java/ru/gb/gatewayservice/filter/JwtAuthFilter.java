package ru.gb.gatewayservice.filter;

import io.jsonwebtoken.Claims;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthFilter extends AbstractGatewayFilterFactory<JwtAuthFilter.Config> {

    private final JwtUtil jwtUtil;

    public JwtAuthFilter(JwtUtil jwtUtil) {
        super(Config.class);
        this.jwtUtil = jwtUtil;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();

            if (request.getHeaders().containsKey("userId")) {     //  защита обхода Gateway
                return this.onError(exchange, "Invalid header username", HttpStatus.BAD_REQUEST);
            }

            if (isAuthPresent(request)) {
                final String token = getAuthHeader(request);
                if (jwtUtil.isInvalid(token)) {
                    return this.onError(exchange, jwtUtil.validateToken(token), HttpStatus.UNAUTHORIZED);
                }
                populateRequestWithHeaders(exchange, token);
            }
            return chain.filter(exchange);
        };
    }

    public static class Config {
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").get(0).substring(7);
    }

    private boolean isAuthPresent(ServerHttpRequest request) {
        if (!request.getHeaders().containsKey("Authorization")) {
            return false;
        }
        return request.getHeaders().getOrEmpty("Authorization").get(0).startsWith("Bearer ");
    }

    private void populateRequestWithHeaders(ServerWebExchange exchange, String token) {
        Claims claims = jwtUtil.getAllClaimsFromToken(token);
        exchange.getRequest().mutate()
                .header("userId", claims.getSubject())
                .header("role", String.valueOf(claims.get("role")))     //  ЕСЛИ В ТОКЕНЕ БУДЕТ role
                .build();
    }
}