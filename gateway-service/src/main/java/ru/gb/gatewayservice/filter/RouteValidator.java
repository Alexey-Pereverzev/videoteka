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
            "/api/v1/price/prices_filter",
            "/api/v1/raiting/list_all",
            "/api/v1/raiting/add_new",
            "/api/v1/raiting/total_film_raiting",
            "/api/v1/raiting/list_all_grade_and_review_by_filmId",
            "/api/v1/raiting/grade_user_by_id_film",

            "/api/v1/cart",
            "/api/v1/cart/generate",
            "/api/v1/cart/add",
            "/api/v1/cart/remove",
            "/api/v1/cart/clear",
            "/api/v1/cart/rediscontent"

    );

    public static final List<String> adminApiEndpoints = List.of(
            "/api/v1/roles/update",
            "/api/v1/users/delete"
    );

    public static final List<String> userApiEndpoints = List.of(
            "/api/v1/cart/merge",
            "/api/v1/cart/pay",

            "/api/v1/orders",
            "/api/v1/orders/playFilm",
            "/api/v1/orders/delete"

    );

    public static final List<String> managerApiEndpoints = List.of(
            "/api/v1/film/add_new"
    );

    public Predicate<ServerHttpRequest> isFreeAccess =
            request -> openApiEndpoints
                    .stream()
                    .anyMatch(uri -> truncateUri(request.getURI().getPath()).equals(uri));

    public Predicate<ServerHttpRequest> isAdminAccess =
            request -> adminApiEndpoints
                    .stream()
                    .anyMatch(uri -> truncateUri(request.getURI().getPath()).equals(uri));

    public Predicate<ServerHttpRequest> isUserAccess =
            request -> userApiEndpoints
                    .stream()
                    .anyMatch(uri -> truncateUri(request.getURI().getPath()).equals(uri));

    public Predicate<ServerHttpRequest> isManagerAccess =
            request -> managerApiEndpoints
                    .stream()
//                    .anyMatch(uri -> request.getURI().getPath().contains(uri));
                    .anyMatch(uri -> truncateUri(request.getURI().getPath()).equals(uri));

    public static String truncateUri(String uri) {
        if (uri.charAt(uri.length()-1)=='/') {
            uri = uri.substring(0,uri.length()-1);
        }
        String withParams = "/".concat(uri.substring(1).split("/",2)[1]);
        String withoutParams = withParams.split("/\\?",2)[0];
        return withoutParams;
    }

}

