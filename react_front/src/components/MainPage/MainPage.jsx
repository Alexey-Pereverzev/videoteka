import style from "./MainPage.module.css"
import Header from "../UI/Header/Header";
import FilmCard from "../../widgets/FilmCard/FilmCard";
import {Component} from "react";
import axios from "axios";
import {Button, ButtonGroup, FormControl, InputLabel, MenuItem, Pagination, Select} from "@mui/material";
import Footer from "../UI/Footer/Footer";
import {Route, Routes} from "react-router-dom";
import CatalogPage from "./CatalogPage/CatalogPage";


class MainPage extends Component {
    constructor(props) {
        super(props);
        // this.handleDirectorsChange = this.handleDirectorsChange.bind(this);
        // this.handleCountriesChange = this.handleCountriesChange.bind(this);
        // this.handleSaleChange = this.handleSaleChange.bind(this);

        this.state = {
            // films: [],
            // genres: [],
            // directors: [],
            // countries: [],
            // filterCountryList: '',
            // filterDirectorList: '',
            // filterGenreList: '',
            // startPremierYear: '',
            // endPremierYear: '',
            // minYear: '',
            // maxYear: '',
            // titlePart: '',
            // isSale: true,
            // minPrice: '',
            // maxPrice: '',
            currentPage: 1,
            active: false,
            modal: false
        }
    }

    componentDidMount() {

        // this.getMinMaxPrice()
        // this.getMinMaxYear()
        // this.getAllGenres()
        // this.getAllDirectors()
        // this.getAllCountries()
    }

    getFilmByTitlePart = (currentPage, value) => {
        const titlePart = value.nativeEvent.data
        currentPage -= 1;
        console.log(titlePart)
        axios.get("http://localhost:5555/catalog/api/v1/film/find_by_title_part",
            {
                params: {
                    currentPage,
                    titlePart
                }
            })
            .then(response => response.data)
            .then((data) => {
                if (data !== null) {
                    console.log(data.content)
                    this.setState({
                        films: data.content,
                        totalPages: data.totalPages,
                        totalElements: data.totalElements,
                        currentPage: data.number + 1
                    })
                } else {
                    if (data === null) {
                        return (
                            <div>
                                <h4>Ничего нет</h4>
                            </div>
                        )
                    }
                }

            }).catch((error) => {
            console.error("Error: " + error)
        })
    }
    // getMinMaxPrice = () => {
    //     axios.get("http://localhost:5555/catalog/api/v1/price/prices_filter")
    //         .then(response => response.data)
    //         .then((data) => {
    //             if (this.state.isSale === true) {
    //                 console.log(data.minPriceSale)
    //                 this.setState({
    //                         minPrice: data.minPriceSale,
    //                         maxPrice: data.maxPriceSale
    //                     },
    //                     () => this.getAllFilms(this.state.currentPage,
    //                         this.state.filterCountryList,
    //                         this.state.filterDirectorList,
    //                         this.state.filterGenreList,
    //                         this.state.startPremierYear,
    //                         this.state.endPremierYear,
    //                         this.state.isSale,
    //                         this.state.minPrice,
    //                         this.state.maxPrice
    //                     )
    //                 )
    //             } else {
    //                 if (this.state.isSale === false) {
    //                     console.log(data)
    //                     this.setState({
    //                         minPrice: data.minPriceRent,
    //                         maxPrice: data.maxPriceRent
    //                     }, () => this.getAllFilms(this.state.currentPage,
    //                         this.state.filterCountryList,
    //                         this.state.filterDirectorList,
    //                         this.state.filterGenreList,
    //                         this.state.startPremierYear,
    //                         this.state.endPremierYear,
    //                         this.state.isSale,
    //                         this.state.minPrice,
    //                         this.state.maxPrice
    //                     ))
    //                 }
    //             }
    //
    //         }).catch((error) => {
    //         console.error("Error: " + error)
    //     })
    // }
    // getMinMaxYear = () => {
    //     axios.get("http://localhost:5555/catalog/api/v1/film/min_max_year")
    //         .then(response => response.data)
    //         .then((data) => {
    //
    //             console.log(data.minYear)
    //             this.setState({
    //                 startPremierYear: data.minYear,
    //                 endPremierYear: data.maxYear
    //                 },
    //             )
    //         }).catch((error) => {
    //         console.error("Error: " + error)
    //     })
    // }
    // getAllGenres = () => {
    //     axios.get("http://localhost:5555/catalog/api/v1/genre/list_all")
    //         .then(response => response.data)
    //         .then((data) => {
    //             console.log(data)
    //             this.setState({
    //                 genres: data
    //
    //             })
    //         }).catch((error) => {
    //         console.error("Error: " + error)
    //     })
    // }
    // getAllDirectors = () => {
    //     axios.get("http://localhost:5555/catalog/api/v1/director/list_all")
    //         .then(response => response.data)
    //         .then((data) => {
    //             console.log(data)
    //             this.setState({
    //                 directors: data
    //
    //             })
    //         }).catch((error) => {
    //         console.error("Error: " + error)
    //     })
    // }
    // getAllCountries = () => {
    //     axios.get("http://localhost:5555/catalog/api/v1/country/list_all")
    //         .then(response => response.data)
    //         .then((data) => {
    //             console.log(data)
    //             this.setState({
    //                 countries: data
    //
    //             })
    //         }).catch((error) => {
    //         console.error("Error: " + error)
    //     })
    // }
    // getAllFilms = (currentPage,
    //                filterCountryList,
    //                filterDirectorList,
    //                filterGenreList,
    //                startPremierYear,
    //                endPremierYear,
    //                isSale,
    //                minPrice,
    //                maxPrice
    // ) => {
    //     currentPage -= 1;
    //     axios.get("http://localhost:5555/catalog/api/v1/film/list_all",
    //         {
    //             params: {
    //                 currentPage,
    //                 filterCountryList,
    //                 filterDirectorList,
    //                 filterGenreList,
    //                 startPremierYear,
    //                 endPremierYear,
    //                 isSale,
    //                 minPrice,
    //                 maxPrice,
    //             }
    //         })
    //         .then(response => response.data)
    //         .then((data) => {
    //             this.setState({
    //                 films: data.content,
    //                 totalPages: data.totalPages,
    //                 totalElements: data.totalElements,
    //                 currentPage: data.number + 1
    //             })
    //         }).catch((error) => {
    //         console.error("Error: " + error)
    //     })
    // }
    //
    // clearState() {
    //     this.setState(
    //         {
    //             filterCountryList: '',
    //             filterDirectorList: '',
    //             filterGenreList: '',
    //             startPremierYear: '',
    //             endPremierYear: '',
    //             isSale: true,
    //             minPrice: '',
    //             maxPrice: '',
    //             currentPage: 1,
    //             active: false
    //         }, () => {this.getMinMaxPrice(); this.getMinMaxYear();}
    //     )
    // }
    //
    //
    // filmFilterByGenres(genre) {
    //     if (genre === "Все") {
    //         this.clearState()
    //     } else {
    //         this.setState(
    //             {
    //                 filterGenreList: genre
    //             }, () => this.getAllFilms(this.state.currentPage,
    //                 this.state.state.filterCountryList,
    //                 this.state.state.filterDirectorList,
    //                 this.state.state.filterGenreList,
    //                 this.state.state.startPremierYear,
    //                 this.state.state.endPremierYear,
    //                 this.state.state.isSale,
    //                 this.state.state.minPrice,
    //                 this.state.state.maxPrice
    //             )
    //         )
    //
    //     }
    //
    //
    // }

    // handleSearchChange (event){
    //     console.log(event.target.value)
    //     this.clearState();
    //             this.setState(
    //                 {
    //                     filterDirectorList: event.target.value
    //                 }, () => this.getAllFilms(this.state.currentPage,
    //                     this.state.filterCountryList,
    //                     this.state.filterDirectorList,
    //                     this.state.filterGenreList,
    //                     this.state.startPremierYear,
    //                     this.state.endPremierYear,
    //                     this.state.isSale,
    //                     this.state.minPrice,
    //                     this.state.maxPrice)
    //             )
    //
    // }
    // handleDirectorsChange(event) {
    //     console.log(event.target.value)
    //     if (event.target.value === "Все") {
    //         this.clearState()
    //     } else {
    //         if (event.target.value !== "Все") {
    //             this.setState(
    //                 {
    //                     filterDirectorList: event.target.value
    //                 }, () => this.getAllFilms(this.state.currentPage,
    //                     this.state.filterCountryList,
    //                     this.state.filterDirectorList,
    //                     this.state.filterGenreList,
    //                     this.state.startPremierYear,
    //                     this.state.endPremierYear,
    //                     this.state.isSale,
    //                     this.state.minPrice,
    //                     this.state.maxPrice)
    //             )
    //         }
    //     }
    // }
    //
    // usePageHandler(num) {
    //     console.log(num)
    //     this.setState(
    //         {
    //             currentPage: num
    //         }, () => this.getAllFilms(this.state.currentPage,
    //             this.state.filterCountryList,
    //             this.state.filterDirectorList,
    //             this.state.filterGenreList,
    //             this.state.startPremierYear,
    //             this.state.endPremierYear,
    //             this.state.isSale,
    //             this.state.minPrice,
    //             this.state.maxPrice)
    //     )
    // }
    //
    // handleCountriesChange(event) {
    //     this.setState(
    //         {
    //             filterCountryList: event.target.value
    //         }, () => this.getAllFilms(this.state.currentPage,
    //             this.state.filterCountryList,
    //             this.state.filterDirectorList,
    //             this.state.filterGenreList,
    //             this.state.startPremierYear,
    //             this.state.endPremierYear,
    //             this.state.isSale,
    //             this.state.minPrice,
    //             this.state.maxPrice)
    //     )
    // }
    //
    // handleDateChange(name, event) {
    //     let value = event.target.value;
    //     if (name === "second") {
    //         if (parseInt(this.state.startPremierYear) <= parseInt(value) && value.length === 4 ) {
    //             this.setState({endPremierYear: value}, () => this.getAllFilms(this.state.currentPage,
    //                 this.state.filterCountryList,
    //                 this.state.filterDirectorList,
    //                 this.state.filterGenreList,
    //                 this.state.startPremierYear,
    //                 this.state.endPremierYear,
    //                 this.state.isSale,
    //                 this.state.minPrice,
    //                 this.state.maxPrice));
    //         }
    //     } else {
    //         if (parseInt(value) <= parseInt(this.state.endPremierYear) && value.length === 4 ) {
    //             this.setState({startPremierYear: value}, () => this.getAllFilms(this.state.currentPage,
    //                 this.state.filterCountryList,
    //                 this.state.filterDirectorList,
    //                 this.state.filterGenreList,
    //                 this.state.startPremierYear,
    //                 this.state.endPremierYear,
    //                 this.state.isSale,
    //                 this.state.minPrice,
    //                 this.state.maxPrice));
    //
    //         }
    //     }
    // }
    //
    // handleSaleChange() {
    //     if (this.state.isSale === true) {
    //         this.setState({isSale: false}, () => this.getMinMaxPrice())
    //     } else {
    //         if (this.state.isSale === false) {
    //             this.setState({isSale: true}, () => this.getMinMaxPrice())
    //         }
    //     }
    //
    // }
    //
    // handlePriceChange(name, event) {
    //     let value = event.target.value;
    //     if (name === "second") {
    //         if (parseInt(this.state.minPrice) <= parseInt(value) ) {
    //             this.setState({maxPrice: value}, () => this.getAllFilms(this.state.currentPage,
    //                 this.state.filterCountryList,
    //                 this.state.filterDirectorList,
    //                 this.state.filterGenreList,
    //                 this.state.startPremierYear,
    //                 this.state.endPremierYear,
    //                 this.state.isSale,
    //                 this.state.minPrice,
    //                 this.state.maxPrice));
    //
    //         }
    //     } else {
    //         if (parseInt(value) <= parseInt(this.state.maxPrice) && parseInt(value) >= parseInt(this.state.minPrice) ) {
    //             this.setState({minPrice: value}, () => this.getAllFilms(this.state.currentPage,
    //                 this.state.filterCountryList,
    //                 this.state.filterDirectorList,
    //                 this.state.filterGenreList,
    //                 this.state.startPremierYear,
    //                 this.state.endPremierYear,
    //                 this.state.isSale,
    //                 this.state.minPrice,
    //                 this.state.maxPrice));
    //
    //         }
    //     }
    // }

    render() {

        // const {films, currentPage, filmsPerPage} = this.state;
        // const genres = this.state.genres;
        // const directors = this.state.directors;
        // const countries = this.state.countries;
        // const totalPages = this.state.totalPages;
        // const {active, setActive} = this.state.active;
        return (
            <div className={style.container}>
                <Header logout={this.props.logout}
                        onChange={(value) => this.getFilmByTitlePart(this.state.currentPage, value)}
                />
                <div className={style.main_container}>
                    <Routes>
                        <Route index path={'/'} element={<CatalogPage/>}/>
                        <Route/>
                        <Route/>
                    </Routes>
                </div>



                {/*<div className={style.genre_bar}>*/}
                {/*    <ButtonGroup variant="text" size="small" aria-label="outlined primary button group">*/}
                {/*        <Button onClick={() => this.filmFilterByGenres("Все")} className={style.unselected}>Все</Button>*/}
                {/*        {genres.map((genre) =>*/}
                {/*            <Button onClick={() => this.filmFilterByGenres(genre.title)}*/}
                {/*                    className={active ? style.selected : style.unselected}>{genre.title}</Button>*/}
                {/*        )}*/}
                {/*    </ButtonGroup>*/}
                {/*</div>*/}

                {/*<div className={style.pagination}>*/}
                {/*    {*/}
                {/*        films.length > 0 ?*/}
                {/*            <div>*/}
                {/*                <div className={style.current_pages}>*/}
                {/*                    <h4>Это {currentPage} страница из {totalPages}</h4>*/}
                {/*                </div>*/}
                {/*                <div className={style.pagination_items}>*/}
                {/*                    <Pagination count={totalPages}*/}
                {/*                                page={currentPage}*/}
                {/*                                color="success"*/}
                {/*                                siblingCount={1}*/}
                {/*                                sx={{backgroundColor: "#414141"}}*/}
                {/*                                onChange={(_, num) => this.usePageHandler(num)}*/}
                {/*                    />*/}
                {/*                </div>*/}
                {/*            </div>*/}
                {/*            :*/}
                {/*            <div className={style.empty}>*/}
                {/*                <h1>Неть</h1>*/}
                {/*            </div>*/}
                {/*    }*/}
                {/*</div>*/}

                {/*<div className={style.catalog}>*/}
                {/*    {*/}
                {/*        films.length === 0 ?*/}
                {/*            <div className={style.empty}>*/}
                {/*                <h1>Неть</h1>*/}
                {/*            </div> :*/}
                {/*            films.map((film) => (*/}
                {/*                <FilmCard imageUrlLink={film.imageUrlLink}*/}
                {/*                          id={film.id}*/}
                {/*                          isSale={this.state.isSale}*/}
                {/*                          salePrice={film.salePrice}*/}
                {/*                          rentPrice={film.rentPrice}*/}
                {/*                          title={film.title}*/}
                {/*                          premierYear={film.premierYear}*/}
                {/*                          country={film.country}*/}
                {/*                          genre={film.genre}*/}
                {/*                          director={film.director}*/}
                {/*                          description={film.description}/>*/}
                {/*            ))*/}
                {/*    }*/}

                {/*</div>*/}

                {/*<div className={style.filter_card}>*/}

                {/*    <FormControl sx={{m: 1, minWidth: 120}} size="small" focused={false}>*/}
                {/*        <InputLabel id="demo-select-small-label">Режиссёр</InputLabel>*/}
                {/*        <Select*/}
                {/*            labelId="demo-select-small-label"*/}
                {/*            id="demo-select-small"*/}
                {/*            name={'director'}*/}
                {/*            value={this.state.filterDirectorList}*/}
                {/*            color={'success'}*/}
                {/*            defaultValue={''}*/}
                {/*            sx={{backgroundColor: 'darkgrey', borderColor: "success"}}*/}
                {/*            label="Age"*/}
                {/*            onChange={this.handleDirectorsChange}*/}
                {/*        >*/}
                {/*            <MenuItem value="Все">*/}
                {/*                <em>Все</em>*/}
                {/*            </MenuItem>*/}
                {/*            {directors.map((director) =>*/}
                {/*                <MenuItem*/}
                {/*                    value={director.firstName + " " + director.lastName}>{director.firstName} {director.lastName}</MenuItem>*/}
                {/*            )}*/}
                {/*        </Select>*/}
                {/*    </FormControl>*/}
                {/*    <div>*/}
                {/*        <FormControl sx={{m: 1, minWidth: 120}} size="small">*/}
                {/*            <InputLabel id="demo-select-small-label">Страна</InputLabel>*/}
                {/*            <Select*/}
                {/*                labelId="country"*/}
                {/*                id="demo-select-small"*/}
                {/*                name={'country'}*/}
                {/*                value={this.state.filterCountryList}*/}
                {/*                color={'success'}*/}
                {/*                defaultValue={'Все'}*/}
                {/*                sx={{backgroundColor: 'darkgrey', borderColor: "success"}}*/}
                {/*                label="Страна"*/}
                {/*                onChange={this.handleCountriesChange}*/}
                {/*            >*/}
                {/*                <MenuItem value={''} onClick={this.handleClear}>*/}
                {/*                    <em>Все</em>*/}
                {/*                </MenuItem>*/}
                {/*                {countries.map((country) =>*/}
                {/*                    <MenuItem value={country.title}>{country.title}</MenuItem>*/}
                {/*                )}*/}
                {/*            </Select>*/}
                {/*        </FormControl>*/}
                {/*    </div>*/}
                {/*    <br/>*/}
                {/*    <span className={style.filter_title}>Годы премьер</span>*/}
                {/*    <div >*/}
                {/*        <div className={style.year_range}>*/}
                {/*            <div className={style.field}>*/}
                {/*                <input type={'number'}*/}
                {/*                       className={style.start__field}*/}
                {/*                       placeholder={this.state.startPremierYear}*/}
                {/*                       onChange={this.handleDateChange.bind(this, "first")}/>*/}
                {/*            </div>*/}
                {/*            <div className={style.separator}><span>-</span></div>*/}
                {/*            <div className={style.field}>*/}
                {/*                <input type={'number'}*/}
                {/*                       className={style.end__field}*/}
                {/*                       placeholder={this.state.endPremierYear}*/}
                {/*                       onChange={this.handleDateChange.bind(this, "second")}/>*/}
                {/*            </div>*/}
                {/*        </div>*/}
                {/*    </div>*/}
                {/*    <br/>*/}
                {/*    {this.state.isSale ?*/}
                {/*        <span className={style.filter_title}>Диапазон цен продажи</span>*/}
                {/*        :*/}
                {/*        <span className={style.filter_title}>Диапазон цен аренды</span>*/}
                {/*    }*/}

                {/*    <div>*/}
                {/*        <div className={style.year_range}>*/}
                {/*            <div className={style.field}>*/}
                {/*                <input type={'number'}*/}
                {/*                       className={style.start__field}*/}
                {/*                       placeholder={this.state.minPrice}*/}
                {/*                       onChange={this.handlePriceChange.bind(this, "first")}/>*/}
                {/*            </div>*/}
                {/*            <div className={style.separator}><span>-</span></div>*/}
                {/*            <div className={style.field}>*/}
                {/*                <input type={'number'}*/}
                {/*                       className={style.end__field}*/}
                {/*                       placeholder={this.state.maxPrice}*/}
                {/*                       onChange={this.handlePriceChange.bind(this, "second") }/>*/}
                {/*            </div>*/}
                {/*        </div>*/}
                {/*    </div>*/}
                {/*    {!this.state.isSale ?*/}
                {/*        <Button onClick={this.handleSaleChange} className={style.clear}>Продажа</Button>*/}
                {/*        :*/}
                {/*        <Button onClick={this.handleSaleChange} className={style.clear}>Аренда</Button>*/}
                {/*    }*/}

                {/*    <Button onClick={() => this.filmFilterByGenres("Все")} className={style.clear}>Сбросить</Button>*/}

                {/*</div>*/}

                <div>
                    <Footer/>
                </div>
            </div>
        )
    }
}

export default MainPage;