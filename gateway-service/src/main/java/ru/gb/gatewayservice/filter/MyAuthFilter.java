package ru.gb.gatewayservice.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class MyAuthFilter extends AbstractGatewayFilterFactory<MyAuthFilter.Config> {

    @Autowired
    private RouteValidator validator;

    public MyAuthFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            boolean requestIsSecured = false;

            if (!validator.isFreeAccess.test(request)) {

                if (getRoleHeader(request).equals("ROLE_ADMIN")) {
                    if (!validator.isAdminAccess.test(request)) {
                        return this.onError(exchange, "Access denied!", HttpStatus.FORBIDDEN);
                    } else {
                        requestIsSecured = true;
                    }
                }

                if (getRoleHeader(request).equals("ROLE_USER")) {
                    if (!validator.isUserAccess.test(request)) {
                        return this.onError(exchange, "Access denied!", HttpStatus.FORBIDDEN);
                    } else {
                        requestIsSecured = true;
                    }
                }

                if (getRoleHeader(request).equals("ROLE_MANAGER")) {
                    if (!validator.isManagerAccess.test(request)) {
                        return this.onError(exchange, "Access denied!", HttpStatus.FORBIDDEN);
                    } else {
                        requestIsSecured = true;
                    }
                }

                if (!requestIsSecured) {
                    return this.onError(exchange, "Not for guests", HttpStatus.UNAUTHORIZED);
                }

            }

            return chain.filter(exchange);
        };
    }

    public static class Config {
    }

    private String getRoleHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("role").get(0);
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }
}