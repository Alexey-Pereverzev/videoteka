import style from "./MainPage.module.css"
import Header from "../UI/Header/Header";
import FilmCard from "../../widgets/FilmCard/FilmCard";
import {Component, useState} from "react";
import axios from "axios";
import {
    Button,
    ButtonGroup,
    FormControl,
    FormHelperText,
    Input,
    InputLabel,
    MenuItem,
    Pagination,
    Select
} from "@mui/material";
import DateRangeField from "../../widgets/DateRangeField/DateRangeField";





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
            filterCountryList: '',
            filterDirectorList: '',
            filterGenreList: '',
            startPremierYear: 1955,
            endPremierYear: 2020,
            isSale: false,
            minPrice: 0,
            maxPrice: 1000,
            currentPage: 1,
            active: false
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
        this.getAllCountries()
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
        // .then(() => this.clearState())

    }

    clearState() {
        this.setState(
            {
                filterCountryList: '',
                filterDirectorList: '',
                filterGenreList: '',
                startPremierYear: 1955,
                endPremierYear: 2020,
                isSale: false,
                minPrice: 0,
                maxPrice: 1000,
                currentPage: 1,
                active: false
            }
        )
    }

    handleClear = () => {
        if (() => this.clearState()) {
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

    }

    filmFilterByGenres(genre) {
        this.clearState()
        this.setState(
            {
                filterGenreList: genre,

            }
        )
        this.getAllFilms(this.state.currentPage,
            this.state.filterCountryList,
            this.state.filterDirectorList,
            genre,
            this.state.startPremierYear,
            this.state.endPremierYear,
            this.state.isSale,
            this.state.minPrice,
            this.state.maxPrice
        )

    }

    handleDirectorsChange(event) {
        this.clearState()
        this.setState(
            {
                filterDirectorList: event.target.value
            }
        )
        this.getAllFilms(this.state.currentPage,
            this.state.filterCountryList,
            event.target.value,
            this.state.filterGenreList,
            this.state.startPremierYear,
            this.state.endPremierYear,
            this.state.isSale,
            this.state.minPrice,
            this.state.maxPrice)

    }

    usePageHandler(num) {
        console.log(num)
        this.setState(
            {
                currentPage: num
            }
        )
        this.getAllFilms(num,
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
        this.clearState()
        this.setState(
            {
                filterCountryList: event.target.value
            }
        )
        this.getAllFilms(this.state.currentPage,
            event.target.value,
            this.state.filterDirectorList,
            this.state.filterGenreList,
            this.state.startPremierYear,
            this.state.endPremierYear,
            this.state.isSale,
            this.state.minPrice,
            this.state.maxPrice)

    }
    handleDateChange(name, event) {
        let value = event.target.value;
        if(name === "second"){
            if(parseInt(this.state.startPremierYear) <= parseInt(value)){
                this.setState({endPremierYear:value});
                this.getAllFilms(this.state.currentPage,
                    this.state.filterCountryList,
                    this.state.filterDirectorList,
                    this.state.filterGenreList,
                    this.state.startPremierYear,
                    value,
                    this.state.isSale,
                    this.state.minPrice,
                    this.state.maxPrice)
            }
        }
        else{
            if(parseInt(value) <= parseInt(this.state.endPremierYear)){
                this.setState({startPremierYear: value});
                this.getAllFilms(this.state.currentPage,
                    this.state.filterCountryList,
                    this.state.filterDirectorList,
                    this.state.filterGenreList,
                    value,
                    this.state.endPremierYear,
                    this.state.isSale,
                    this.state.minPrice,
                    this.state.maxPrice)
            }
        }


    }
    handlePriceChange(name, event) {
        let value = event.target.value;
        if(name === "second"){
            if(parseInt(this.state.minPrice) <= parseInt(value)){
                this.setState({maxPrice:value});
                this.getAllFilms(this.state.currentPage,
                    this.state.filterCountryList,
                    this.state.filterDirectorList,
                    this.state.filterGenreList,
                    this.state.startPremierYear,
                    this.state.endPremierYear,
                    this.state.isSale,
                    this.state.minPrice,
                    value)
            }
        }
        else{
            if(parseInt(value) <= parseInt(this.state.maxPrice)){
                this.setState({minPrice: value});
                this.getAllFilms(this.state.currentPage,
                    this.state.filterCountryList,
                    this.state.filterDirectorList,
                    this.state.filterGenreList,
                    this.state.startPremierYear,
                    this.state.endPremierYear,
                    this.state.isSale,
                    value,
                    this.state.maxPrice)
            }
        }


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
        const {active, setActive} = this.state
        return (
            <div className={style.container}>
                <Header logout={this.props.logout}/>
                <div className={style.genre_bar}>
                    <ButtonGroup variant="text" size="small" aria-label="outlined primary button group">
                        <Button onClick={this.handleClear} className={style.unselected}>Все</Button>
                        {genres.map((genre) =>
                            <Button onClick={() => this.filmFilterByGenres(genre.title)}
                                    className={active? style.selected : style.unselected}>{genre.title}</Button>
                        )}
                    </ButtonGroup>
                </div>

                <div className={style.pagination}>
                    {
                        films.length > 0 ?
                            <div>
                                <div className={style.current_pages}>
                                    <h4>Это {currentPage} страница из {totalPages}</h4>
                                </div>
                                <div className={style.pagination_items}>
                                    <Pagination count={totalPages}
                                                page={currentPage}
                                                color="success"
                                                siblingCount={1}
                                                sx={{backgroundColor: "#414141"}}
                                                onChange={(_, num) => this.usePageHandler(num)}
                                    />
                                </div>
                            </div>
                            :
                            <div className={style.empty}>
                                <h1>Неть</h1>
                            </div>
                    }
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

                </div>

                <div className={style.filter_card}>

                    <FormControl sx={{m: 1, minWidth: 120}} size="small" success>
                        <InputLabel id="demo-select-small-label">Режиссёр</InputLabel>
                        <Select
                            labelId="demo-select-small-label"
                            id="demo-select-small"
                            name={'director'}
                            value={this.state.filterDirectorList}
                            color={'success'}
                            defaultValue={''}
                            sx={{backgroundColor: 'darkgrey', borderColor: "success"}}
                            label="Age"
                            onChange={this.handleDirectorsChange}
                        >
                            <MenuItem value={''} onClick={this.handleClear}>
                                <em>Все</em>
                            </MenuItem>
                            {directors.map((director) =>
                                <MenuItem
                                    value={director.firstName + " " + director.lastName}>{director.firstName} {director.lastName}</MenuItem>
                            )}
                        </Select>
                    </FormControl>
                    <div>
                        <FormControl sx={{m: 1, minWidth: 120}} size="small">
                            <InputLabel id="demo-select-small-label">Страна</InputLabel>
                            <Select
                                labelId="country"
                                id="demo-select-small"
                                name={'country'}
                                value={this.state.filterCountryList}
                                color={'success'}
                                defaultValue={''}
                                sx={{backgroundColor: 'darkgrey', borderColor: "success"}}
                                label="Страна"
                                placeHolderColor
                                onChange={this.handleCountriesChange}
                            >
                                <MenuItem value={''} onClick={this.handleClear}>
                                    <em>Все</em>
                                </MenuItem>
                                {countries.map((country) =>
                                    <MenuItem value={country.title}>{country.title}</MenuItem>
                                )}
                            </Select>
                        </FormControl>
                    </div>
                    <br/>
                    <span className={style.filter_title}>Годы премьер</span>
                    <div>
                        <div className={style.year_range}>
                            <div className={style.field}>
                                {/*<label>с</label>*/}
                                <input type={'number'}
                                       className={style.start__field}
                                       placeholder={'с'}
                                       onChange={this.handleDateChange.bind(this, "first")}/>
                            </div>
                            <div className={style.separator}><span>-</span></div>
                            <div className={style.field}>
                                {/*<label>по</label>*/}
                                <input type={'number'}
                                       className={style.end__field}
                                       placeholder={'по'}
                                       onChange={this.handleDateChange.bind(this, "second")}/>
                            </div>
                        </div>
                    </div>
                    <br/>
                    <span className={style.filter_title}>Диапазон цен</span>
                    <div>
                        <div className={style.year_range}>
                            <div className={style.field}>
                                <input type={'number'}
                                       className={style.start__field}
                                       placeholder={'от'}
                                       onChange={this.handlePriceChange.bind(this, "first")}/>
                            </div>
                            <div className={style.separator}><span>-</span></div>
                            <div className={style.field}>
                                <input type={'number'}
                                       className={style.end__field}
                                       placeholder={'до'}
                                       onChange={this.handlePriceChange.bind(this, "second")}/>
                            </div>
                        </div>
                    </div>


                </div>

            </div>
        )
    }


}

export default MainPage;