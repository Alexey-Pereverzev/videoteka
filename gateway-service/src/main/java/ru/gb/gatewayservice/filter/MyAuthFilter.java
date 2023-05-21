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

import java.sql.SQLOutput;

@Component
public class MyAuthFilter extends AbstractGatewayFilterFactory<MyAuthFilter.Config> {

    private final RouteValidator validator;

    public MyAuthFilter(RouteValidator validator) {
        super(Config.class);
        this.validator = validator;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            boolean requestIsSecured = false;


            if (!validator.isFreeAccess.test(request)) {

                if (getRoleHeader(request).equals("ROLE_ADMIN")) {
                    if (!validator.isAdminAccess.test(request)) {
//                        System.out.println("2222");
//                        System.out.println(request.getURI().getPath());
                        return this.onError(exchange, "Access denied!", HttpStatus.FORBIDDEN);
                    } else {
                        requestIsSecured = true;
                    }
                }

                if (getRoleHeader(request).equals("ROLE_USER")) {
                    if (!validator.isUserAccess.test(request)) {
//                        System.out.println("3333");
//                        System.out.println(request.getURI().getPath());
//                        System.out.println(validator.truncateUri(request.getURI().getPath()));
                        return this.onError(exchange, "Access denied!", HttpStatus.FORBIDDEN);
                    } else {
                        requestIsSecured = true;
                    }
                }

                if (getRoleHeader(request).equals("ROLE_MANAGER")) {
                    if (!validator.isManagerAccess.test(request)) {
//                        System.out.println("4444");
//                        System.out.println(request.getURI().getPath());
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