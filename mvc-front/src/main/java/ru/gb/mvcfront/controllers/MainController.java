package ru.gb.mvcfront.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String index() {
        return "redirect:/film/listall?page=0&startPremierYear=1000&endPremierYear=3000&isSale=true&maxPriceRent=100000&maxPriceSale=100000";
    }

    @GetMapping("/login")
    public String login(){
        return "/login";
    }
}