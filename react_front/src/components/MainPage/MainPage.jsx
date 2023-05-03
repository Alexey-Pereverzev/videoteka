import style from "./MainPage.module.css"
import Header from "../UI/Header/Header";
import FilmCard from "../../widgets/FilmCard/FilmCard";
import {Component, useState} from "react";
import axios from "axios";
import {Button, ButtonGroup, FormControl, InputLabel, MenuItem, Pagination, Select} from "@mui/material";


class MainPage extends Component {
    constructor(props) {
        super(props);
        this.handleDirectorsChange = this.handleDirectorsChange.bind(this);
        this.handleCountriesChange = this.handleCountriesChange.bind(this);
        this.state = {
            films: [],
            genres: [],
            directors: [],
            countries: [],
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
    getAllCountries = () => {
        axios.get("http://localhost:5555/catalog/api/v1/country/list_all")
            .then(response => response.data)
            .then((data) => {
                console.log(data)
                this.setState({
                    countries: data

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

    handleDirectorsChange(event) {
        console.log(event.target.value)
        const director = event.target.value;
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
            this.state.maxPrice)
    }

    handleCountriesChange(event) {
        console.log(event.target.value)
        const county = event.target.value;
        this.setState({
            filterDirectorList: country
        });
        this.getAllFilms(this.state.currentPage,
            this.state.filterCountryList,
            this.state.filterDirectorList,
            this.state.filterGenreList,
            this.state.startPremierYear,
            this.state.endPremierYear,
            this.state.isSale,
            this.state.minPrice,
            this.state.maxPrice)
    }
    render() {

        const {films, currentPage, filmsPerPage} = this.state;
        const [selectedDirectors, setSelectedDirectors] = this.state.filterDirectorList;
        const genres = this.state.genres;
        const directors = this.state.directors;
        const countries = this.state.countries;
        const lastIndex = currentPage * filmsPerPage;
        const firstIndex = lastIndex - filmsPerPage;
        const totalPages = this.state.totalPages;
        return (
            <div className={style.container}>
                <Header logout={this.props.logout}/>
                <div className={style.genre_bar}>
                    <ButtonGroup variant="text" size="small" aria-label="outlined primary button group">
                        {genres.map((genre) =>
                            <Button onClick={() =>this.filmFilterByGenres(genre.title)}>{genre.title}</Button>
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

                   <FormControl sx={{ m: 1, minWidth: 120 }} size="small">
                       <InputLabel id="demo-select-small-label">Режиссёр</InputLabel>
                       <Select
                           labelId="demo-select-small-label"
                           id="demo-select-small"
                           name={'director'}
                           value={this.state.filterDirectorList}
                           color={'success'}
                           label="Age"
                           onChange={this.handleDirectorsChange}
                       >
                           <MenuItem value="">
                               <em>None</em>
                           </MenuItem>
                           {directors.map((director) =>
                           <MenuItem value={director.firstName +" "+ director.lastName} >{director.firstName} {director.lastName}</MenuItem>
                           )}
                       </Select>
                   </FormControl>
                   <div>
                       <FormControl sx={{ m: 1, minWidth: 120 }} size="small">
                           <InputLabel id="demo-select-small-label">Страна</InputLabel>
                           <Select
                               labelId="demo-select-small-label"
                               id="demo-select-small"
                               name={'director'}
                               value={this.state.filterCountryList}
                               color={'success'}
                               label="Страна"
                               onChange={this.handleCountriesChange}
                           >
                               <MenuItem value="">
                                   <em>None</em>
                               </MenuItem>
                               {countries.map((country) =>
                                   <MenuItem value={country.title}>{country.title}</MenuItem>
                               )}
                           </Select>
                       </FormControl>
                   </div>


               </div>

            </div>
        )
    }


}

export default MainPage;