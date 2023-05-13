package ru.gb.mvcfront.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gb.api.dtos.*;
import ru.gb.mvcfront.integrations.AuthorizationServiceIntegration;
import ru.gb.mvcfront.integrations.CatalogServiceIntegration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/film")
public class FilmController {
    private final CatalogServiceIntegration catalogServiceIntegration;
    private final AuthorizationServiceIntegration authorizationServiceIntegration;
    @GetMapping("/find_by_id")
    public String getFilmById(Model model, @RequestParam Long id) {
        FilmDto filmDto;
        filmDto=catalogServiceIntegration.findById(id);
        JwtResponse jwtResponse=authorizationServiceIntegration.getToken("admin","admin");
        model.addAttribute("film", filmDto);
        return "index";
    }

    @GetMapping("/listall")
    public String getAllFilms(Model model, @RequestParam Integer page,
                              @RequestParam (required=false) String[] filterCountryList, @RequestParam (required=false) String[] filterDirectorList, @RequestParam (required=false) String[] filterGenreList,
                              @RequestParam  (required=false) Integer startPremierYear, @RequestParam  (required=false) Integer endPremierYear,
                              @RequestParam  Boolean isSale, @RequestParam Integer maxPriceRent, @RequestParam Integer maxPriceSale) throws UnsupportedEncodingException {
        Integer maxPrice;
        if (isSale){
            maxPrice=maxPriceSale;
        }else{
            maxPrice=maxPriceRent;
        }
        PageFilmDto pageData=catalogServiceIntegration.getListAllFilms(page, filterCountryList,filterDirectorList,filterGenreList,
                startPremierYear,endPremierYear,isSale,0,maxPrice);
        int[] pageList=new int[pageData.getTotalPages()];
        for (int i=0; i<pageData.getTotalPages(); i++){
            pageList[i]=i;
        }
        List<CountryDto> countryDtoList=catalogServiceIntegration.countryDtoList();
        List<DirectorDto> directorDtoList=catalogServiceIntegration.directorDtoList();
        List<GenreDto> genreDtoList=catalogServiceIntegration.genreDtoList();
        PriceDto priceDto=catalogServiceIntegration.priceDto();
        MinMaxYearDto minMaxYearDto=catalogServiceIntegration.minMaxYearDto();
        FilterDataDto filterDataDto=new FilterDataDto();
        filterDataDto.setFilterCountryList(filterCountryList);
        filterDataDto.setFilterDirectorList(filterDirectorList);
        filterDataDto.setFilterGenreList(filterGenreList);
        if (startPremierYear<minMaxYearDto.getMinYear() && endPremierYear>minMaxYearDto.getMaxYear()){
            filterDataDto.setStartPremierYear(minMaxYearDto.getMinYear());
            filterDataDto.setEndPremierYear(minMaxYearDto.getMaxYear());
        }else{
            filterDataDto.setStartPremierYear(startPremierYear);
            filterDataDto.setEndPremierYear(endPremierYear);
        }
        if (isSale){
            filterDataDto.setCheck("on");
            filterDataDto.setSale(true);
        }else{
            filterDataDto.setCheck(null);
            filterDataDto.setSale(false);
        }
        if (maxPrice==100000){
            filterDataDto.setMaxPriceRent(priceDto.getMaxPriceRent());
            filterDataDto.setMaxPriceSale(priceDto.getMaxPriceSale());
        }else{
            filterDataDto.setMaxPriceRent(maxPriceRent);
            filterDataDto.setMaxPriceSale(maxPriceSale);
        }
        model.addAttribute("films",pageData);
        model.addAttribute("pageList",pageList);
        model.addAttribute("countries",countryDtoList);
        model.addAttribute("directors",directorDtoList);
        model.addAttribute("genres",genreDtoList);
        model.addAttribute("prices",priceDto);
        model.addAttribute("years",minMaxYearDto);
        model.addAttribute("filterDataDto",filterDataDto);
        model.addAttribute("linksForPagination",prepareLinksForPagination(pageData.getTotalPages(),filterDataDto));
        return "/catalog/main";
    }

    @PostMapping("/filter_update")
    public String filterUpdate(@ModelAttribute("filterCountry") FilterDataDto filterDataDto,Model model) throws UnsupportedEncodingException {
        return "redirect:/film/listall?page=0&"+convertArrayToString("filterCountryList",filterDataDto.getFilterCountryList())+
                "&"+convertArrayToString("filterDirectorList",filterDataDto.getFilterDirectorList())+
                "&"+convertArrayToString("filterGenreList",filterDataDto.getFilterGenreList())+
                "&startPremierYear="+ filterDataDto.getStartPremierYear()+"&endPremierYear="+filterDataDto.getEndPremierYear()+
                "&isSale="+ filterDataDto.isSale()+"&maxPriceRent="+filterDataDto.getMaxPriceRent()+"&maxPriceSale="+filterDataDto.getMaxPriceSale();
    }

    private String convertArrayToString(String param, String[] str) throws UnsupportedEncodingException {
        String result = "";
        if (str!=null){
            for (int i = 0; i < str.length; i++) {
                if (i != (str.length - 1)) {
                    result = result + param + "=" + URLEncoder.encode(str[i], "utf-8") + "&";
                } else {
                    result = result + param + "=" + URLEncoder.encode(str[i], "utf-8");
                }
            }
        }
        return result;
    }

    private String[] prepareLinksForPagination(int numPages, FilterDataDto filterDataDto) throws UnsupportedEncodingException {
        String[] results=new String[numPages];
        for (int i=0; i<numPages;i++){
            results[i]="/film/listall?page="+i+"&"+convertArrayToString("filterCountryList",filterDataDto.getFilterCountryList())+
                    "&"+convertArrayToString("filterDirectorList",filterDataDto.getFilterDirectorList())+
                    "&"+convertArrayToString("filterGenreList",filterDataDto.getFilterGenreList())+
                    "&startPremierYear="+ filterDataDto.getStartPremierYear()+"&endPremierYear="+filterDataDto.getEndPremierYear()+
                    "&isSale="+ filterDataDto.isSale()+"&maxPriceRent="+filterDataDto.getMaxPriceRent()+"&maxPriceSale="+filterDataDto.getMaxPriceSale();
        }
        return results;
    }

}