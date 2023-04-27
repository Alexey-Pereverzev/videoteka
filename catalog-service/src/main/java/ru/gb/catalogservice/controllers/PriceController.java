package ru.gb.catalogservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.catalogservice.entities.Genre;
import ru.gb.catalogservice.entities.Price;
import ru.gb.catalogservice.services.GenreService;
import ru.gb.catalogservice.services.PriceService;

@RestController
@RequestMapping("/api/v1/price")
@RequiredArgsConstructor
//@CrossOrigin(origins="http://localhost:3000")
public class PriceController {
    private final PriceService priceService;
    @GetMapping("list_all")
    public Page<Price> getUserOrders(){
        return priceService.findAll();
    }
}
