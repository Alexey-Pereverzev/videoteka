package ru.gb.mvcfront.controllers;

import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gb.api.dtos.FilmDto;
import ru.gb.api.dtos.JwtResponse;
import ru.gb.mvcfront.integrations.AuthorizationServiceIntegration;
import ru.gb.mvcfront.integrations.CatalogServiceIntegration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
        System.out.println(filmDto);
        JwtResponse jwtResponse=authorizationServiceIntegration.getToken("admin","admin");
        System.out.println(jwtResponse.getToken());
        model.addAttribute("film", filmDto);
        return "index";
    }

    @GetMapping("/listall/{page}")
    public String getAllFilms(Model model, @PathVariable Integer page){
        System.out.println(page);
        Page<FilmDto> pageData=catalogServiceIntegration.getListAllFilms(0, null,null,null,
                                                                            1900,2023,true,0,1000);
        System.out.println(pageData);
        return "/index";
    }

}
