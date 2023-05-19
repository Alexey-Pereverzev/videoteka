package ru.gb.gatewayservice.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/api/v1/reg/register",
            "/api/v1/auth/authenticate",

            "/api/v1/country/list_all",
            "/api/v1/country/find_by_id",
            "/api/v1/director/list_all",
            "/api/v1/director/find_by_id",
            "/api/v1/film/list_all",
            "/api/v1/film/list_all_dto",
            "/api/v1/film/find_by_title_part",
            "/api/v1/film/find_by_id",
            "/api/v1/film/min_max_year",
            "/api/v1/genre/list_all",
            "/api/v1/genre/find_by_id",
            "/api/v1/price/prices_filter"
            );

    public static final List<String> adminApiEndpoints = List.of(
            "/api/v1/roles/update",
            "/api/v1/users/delete"
    );

    public static final List<String> userApiEndpoints = List.of(

    );

    public static final List<String> managerApiEndpoints = List.of(

    );

    public Predicate<ServerHttpRequest> isFreeAccess =
            request -> openApiEndpoints
                    .stream()
                    .anyMatch(uri -> request.getURI().getPath().contains(uri));

    public Predicate<ServerHttpRequest> isAdminAccess =
            request -> adminApiEndpoints
                    .stream()
                    .anyMatch(uri -> request.getURI().getPath().contains(uri));

    public Predicate<ServerHttpRequest> isUserAccess =
            request -> userApiEndpoints
                    .stream()
                    .anyMatch(uri -> request.getURI().getPath().contains(uri));

    public Predicate<ServerHttpRequest> isManagerAccess =
            request -> managerApiEndpoints
                    .stream()
                    .anyMatch(uri -> request.getURI().getPath().contains(uri));
}

