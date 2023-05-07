package ru.gb.mvcfront.integrations;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import ru.gb.api.dtos.FilmDto;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class CatalogServiceIntegration {
    private final WebClient catalogServiceWebClient;

    public FilmDto findById(Long id) {
        return catalogServiceWebClient.get()
                .uri("/api/v1/film/find_by_id?id=" + id)
                .retrieve()
                .bodyToMono(FilmDto.class)
                .block();
    }

    public Page<FilmDto> getListAllFilms(int page, String[] filterCountryList, String[] filterDirectorList, String[] filterGenreList,
                                         int startPremierYear, int endPremierYear,
                                         boolean isSale, int minPrice, int maxPrice) {
        String filterCountry = convertArrayToString("filterCountryList", filterCountryList);
        System.out.println(filterCountry);
        String filterDirector = convertArrayToString("filterDirectorList", filterDirectorList);
        System.out.println(filterDirector);
        String filterGenre = convertArrayToString("filterGenreList", filterGenreList);
        System.out.println(filterGenre);
        String s="/api/v1/film/list_all?" + "currentPage=" + page +
                "&" + filterCountry + "&" + filterDirector + "&" + filterGenre
                + "&startPremierYear="+startPremierYear+"&endPremierYear="+endPremierYear
                + "&isSale="+isSale+"&minPrice="+minPrice+"&maxPrice="+maxPrice;
        System.out.println(s);
        return catalogServiceWebClient.get()
                .uri(s)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Page<FilmDto>>() {
                })
                .block();
    }

    private String convertArrayToString(String param, String[] str) {
        String result = "";
        if (str!=null){
            for (int i = 0; i < str.length; i++) {
                if (i != (str.length - 1)) {
                    result = result + param + "=" + str[i] + "&";
                } else {
                    result = result + param + "=" + str[i];
                }
            }
        }
        return result;
    }
}
//    public Page<FilmDto> listAll(@RequestParam @Parameter(description = "Номер страницы (start=0)", required = true) int currentPage,
//                                 @RequestParam (name="filterCountryList",required = false) String[] filterCountryList,
//                                 @RequestParam (name="filterDirectorList",required = false) String[] filterDirectorList,
//                                 @RequestParam (name="filterGenreList",required = false) String[] filterGenreList,
//                                 @RequestParam (name="startPremierYear",required = false)Integer startPremierYear,
//                                 @RequestParam (name="endPremierYear",required = false)Integer endPremierYear,
//                                 @RequestParam (name="isSale",required = false)Boolean isSale,
//                                 @RequestParam (name="minPrice",required = false)Integer minPrice,
//                                 @RequestParam (name="maxPrice",required = false)Integer maxPrice){