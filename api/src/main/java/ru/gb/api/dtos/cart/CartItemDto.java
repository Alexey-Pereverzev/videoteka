package ru.gb.api.dtos.cart;

import java.util.List;

public class CartItemDto {
    private Long id;
    private String title;
    private int premierYear;
    private String imageUrlLink;
    private List<String> genre;
    private List<String> country;
    private List<String> director;
    private int rentPrice;
    private int salePrice;
    private int quantity;
    private int pricePerFilm;
    private int price;

    public CartItemDto(Long id, String title, int premierYear, String imageUrlLink, List<String> genre, List<String> country, List<String> director, int rentPrice, int salePrice, int quantity, int pricePerFilm, int price) {
        this.id = id;
        this.title = title;
        this.premierYear = premierYear;
        this.imageUrlLink = imageUrlLink;
        this.genre = genre;
        this.country = country;
        this.director = director;
        this.rentPrice = rentPrice;
        this.salePrice = salePrice;
        this.quantity = quantity;
        this.pricePerFilm = pricePerFilm;
        this.price = price;
    }
    public CartItemDto() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPremierYear() {
        return premierYear;
    }

    public void setPremierYear(int premierYear) {
        this.premierYear = premierYear;
    }

    public String getImageUrlLink() {
        return imageUrlLink;
    }

    public void setImageUrlLink(String imageUrlLink) {
        this.imageUrlLink = imageUrlLink;
    }

    public List<String> getGenre() {
        return genre;
    }

    public void setGenre(List<String> genre) {
        this.genre = genre;
    }

    public List<String> getCountry() {
        return country;
    }

    public void setCountry(List<String> country) {
        this.country = country;
    }

    public List<String> getDirector() {
        return director;
    }

    public void setDirector(List<String> director) {
        this.director = director;
    }

    public int getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(int rentPrice) {
        this.rentPrice = rentPrice;
    }

    public int getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(int salePrice) {
        this.salePrice = salePrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPricePerFilm() {
        return pricePerFilm;
    }

    public void setPricePerFilm(int pricePerFilm) {
        this.pricePerFilm = pricePerFilm;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
