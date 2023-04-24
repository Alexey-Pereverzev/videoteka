package ru.gb.catalogservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.catalogservice.entities.Director;
import ru.gb.catalogservice.entities.Film;
import ru.gb.catalogservice.services.DirectorService;
import ru.gb.catalogservice.services.FilmService;

@RestController
@RequestMapping("/api/v1/film")
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;
    @GetMapping("list_all")
    public Page<Film> getUserOrders(){
        return filmService.findAll();
    }
}
