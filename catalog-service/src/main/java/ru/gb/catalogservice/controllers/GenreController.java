package ru.gb.catalogservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.catalogservice.entities.Film;
import ru.gb.catalogservice.entities.Genre;
import ru.gb.catalogservice.services.FilmService;
import ru.gb.catalogservice.services.GenreService;

@RestController
@RequestMapping("/api/v1/genre")
@RequiredArgsConstructor
//@CrossOrigin(origins="http://localhost:3000")
public class GenreController {
    private final GenreService genreService;
    @GetMapping("list_all")
    public Page<Genre> getUserOrders(){
        return genreService.findAll();
    }
}
