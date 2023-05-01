import style from "./MainPage.module.css"
import Header from "../UI/Header/Header";
import FilmCard from "../../widgets/FilmCard/FilmCard";
import {Component, useState} from "react";
import axios from "axios";
import {Button, ButtonGroup, Pagination} from "@mui/material";


class MainPage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            films: [],
            genres: [],
            directors: [],
            filterCountryList: [],
            filterDirectorList: [],
            filterGenreList: [],
            startPremierYear: 1985,
            endPremierYear: 2020,
            isSale: false,
            minPrice: 0,
            maxPrice: 1000,
            currentPage: 1
        }

    }

    componentDidMount() {
        this.getAllFilms(this.state.currentPage,
            this.state.filterCountryList,
            this.state.filterDirectorList,
            this.state.filterGenreList,
            this.state.startPremierYear,
            this.state.endPremierYear,
            this.state.isSale,
            this.state.minPrice,
            this.state.maxPrice
        )
        this.getAllGenres()
        this.getAllDirectors()
    }

    getAllGenres = () => {
        axios.get("http://localhost:5555/catalog/api/v1/genre/list_all")
            .then(response => response.data)
            .then((data) => {
                console.log(data)
                this.setState({
                    genres: data

                })
            }).catch((error) => {
            console.error("Error: " + error)
        })
    }
    getAllDirectors = () => {
        axios.get("http://localhost:5555/catalog/api/v1/director/list_all")
            .then(response => response.data)
            .then((data) => {
                console.log(data)
                this.setState({
                    directors: data

                })
            }).catch((error) => {
            console.error("Error: " + error)
        })
    }
    getAllFilms = (currentPage,
                   filterCountryList,
                   filterDirectorList,
                   filterGenreList,
                   startPremierYear,
                   endPremierYear,
                   isSale,
                   minPrice,
                   maxPrice
    ) => {
        currentPage -= 1;
        axios.get("http://localhost:5555/catalog/api/v1/film/list_all",
            {
                params: {
                    currentPage,
                    filterCountryList,
                    filterDirectorList,
                    filterGenreList,
                    startPremierYear,
                    endPremierYear,
                    isSale,
                    minPrice,
                    maxPrice,
                }
            })
            .then(response => response.data)
            .then((data) => {
                this.setState({
                    films: data.content,
                    totalPages: data.totalPages,
                    totalElements: data.totalElements,
                    currentPage: data.number + 1
                })
            }).catch((error) => {
            console.error("Error: " + error)
        })
    }
    filmFilter(director) {
        this.setState({
            filterDirectorList: director
        });
        this.getAllFilms(this.state.currentPage,
            this.state.filterCountryList,
            this.state.filterDirectorList,
            this.state.filterGenreList,
            this.state.startPremierYear,
            this.state.endPremierYear,
            this.state.isSale,
            this.state.minPrice,
            this.state.maxPrice
        )
    }
    filmFilterByGenres(genre) {
        this.setState({
            filterGenreList: genre,
        });
        this.getAllFilms(this.state.currentPage,
            this.state.filterCountryList,
            this.state.filterDirectorList,
            this.state.filterGenreList,
            this.state.startPremierYear,
            this.state.endPremierYear,
            this.state.isSale,
            this.state.minPrice,
            this.state.maxPrice
            )
    }

    render() {


        const {films, currentPage, filmsPerPage} = this.state;
        const genres = this.state.genres;
        const lastIndex = currentPage * filmsPerPage;
        const firstIndex = lastIndex - filmsPerPage;
        const totalPages = this.state.totalPages;
        return (
            <div className={style.container}>
                <Header logout={this.props.logout}/>
                <div className={style.genre_bar}>
                    <ButtonGroup variant="text" size="small" aria-label="outlined primary button group">
                        {genres.map((genre) =>
                            <Button onClick={() => this.filmFilterByGenres(genre.title)}>{genre.title}</Button>
                        )}
                    </ButtonGroup>
                </div>
                <div className={style.catalog}>
                    {
                        films.length === 0 ?
                            <div className={style.empty}>
                                <h1>Неть</h1>
                            </div> :
                            films.map((film) => (
                                <FilmCard imageUrlLink={film.imageUrlLink}
                                          id={film.id}
                                          title={film.title}
                                          premierYear={film.premierYear}
                                          country={film.country}
                                          genre={film.genre}
                                          director={film.director}
                                          description={film.description}/>
                            ))
                    }
                    <div className={style.pagination}>
                        {
                            films.length > 0 ?
                                <div>
                                    <div className={style.current_pages}>
                                        <h4>Это {currentPage} страница из {totalPages}</h4>
                                    </div>
                                    <div className={style.pagination_items}>
                                        <Pagination count={10} color="secondary" />
                                    </div>
                                </div>
                                :
                                <div className={style.empty}>
                                    <h1>Неть</h1>
                                </div>
                        }
                    </div>
                </div>

               <div className={style.filter_card}>

               </div>

            </div>
        )
    }


}

export default MainPage;