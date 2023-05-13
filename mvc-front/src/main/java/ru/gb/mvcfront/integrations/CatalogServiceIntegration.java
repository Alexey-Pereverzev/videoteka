package ru.gb.mvcfront.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.gb.api.dtos.*;

import java.time.LocalDate;
import java.util.List;

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

    public PageFilmDto getListAllFilms(Integer page, String[] filterCountryList, String[] filterDirectorList, String[] filterGenreList,
                                       Integer startPremierYear, Integer endPremierYear,
                                       Boolean isSale, Integer minPrice, Integer maxPrice) {
        String filterCountry = convertArrayToString("filterCountryList", filterCountryList);
        String filterDirector = convertArrayToString("filterDirectorList", filterDirectorList);
        String filterGenre = convertArrayToString("filterGenreList", filterGenreList);
        if (startPremierYear==null){
            startPremierYear=1900;
        }
        if (endPremierYear==null){
            endPremierYear= LocalDate.now().getYear();
        }
        String s="/api/v1/film/list_all_dto?" + "currentPage=" + page +
                "&" + filterCountry + "&" + filterDirector + "&" + filterGenre
                + "&startPremierYear="+startPremierYear+"&endPremierYear="+endPremierYear
                + "&isSale="+isSale+"&minPrice="+minPrice+"&maxPrice="+maxPrice;
        return catalogServiceWebClient.get()
                .uri(s)
                .retrieve()
                .bodyToMono(PageFilmDto.class)
                .block();
    }

    public List<CountryDto> countryDtoList (){
        return catalogServiceWebClient.get()
                .uri("/api/v1/country/list_all")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<CountryDto>>() {
                })
                .block();
    }

    public List<DirectorDto> directorDtoList (){
        return catalogServiceWebClient.get()
                .uri("/api/v1/director/list_all")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<DirectorDto>>() {
                })
                .block();
    }

    public List<GenreDto> genreDtoList (){
        return catalogServiceWebClient.get()
                .uri("/api/v1/genre/list_all")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<GenreDto>>() {
                })
                .block();
    }

    public PriceDto priceDto(){
        return catalogServiceWebClient.get()
                .uri("/api/v1/price/prices_filter")
                .retrieve()
                .bodyToMono(PriceDto.class)
                .block();
    }
    public MinMaxYearDto minMaxYearDto(){
        return catalogServiceWebClient.get()
                .uri("/api/v1/film/min_max_year")
                .retrieve()
                .bodyToMono(MinMaxYearDto.class)
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