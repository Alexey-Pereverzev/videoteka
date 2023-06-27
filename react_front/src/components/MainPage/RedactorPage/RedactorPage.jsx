import style from "./RedactorPage.module.css"
import React, {useEffect, useState} from "react";
import {
    Box,
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    FormControl,
    InputLabel,
    MenuItem,
    Select
} from "@mui/material";
import axios from "axios";
import {PlusIcon} from "primereact/icons/plus";
import {toast} from "react-toastify";
import ListCard from "../../../widgets/ListCard/ListCard";
import ListCardDeleteable from "../../../widgets/ListCardDeleteable/ListCardDeleteable";

// Long id;
// String title;
// Integer premierYear;
// String description;
// String imageUrlLink;
// List<String> genre;
// List<String> country;
// List<String> director;
// Integer rentPrice;
// Integer salePrice;
const RedactorPage = () => {

    let getAllGenres = () => {
        axios.get("http://localhost:5555/catalog/api/v1/genre/all")
            .then(response => response.data)
            .then((data) => {
                console.log(data)
                setGenres(data)
            }).catch((error) => {
            console.error("Error: " + error)
        })
    }

    async function getAllFilms() {
        try {
            return await axios.get('http://localhost:5555/catalog/api/v1/film/all')
                .then((response) => {
                        setFilms(response.data)
                    },
                    function errorCallback(response) {
                        console.log(response)
                        let displayCartNotification = (message) => {
                            toast.error(message);
                        }
                        displayCartNotification(response.response.data.value)
                    })
        } catch (e) {

        }
    }

    let getAllDirectors = () => {
        axios.get('http://localhost:5555/catalog/api/v1/director/all')
            .then(r => r.data)
            .then(data => {
                setDirectors(data)
            })
    }
    let getAllCountries = () => {
        axios.get('http://localhost:5555/catalog/api/v1/country/all')
            .then(r => r.data)
            .then(data => {
                setCountries(data)
            })
    }
    useEffect(() => {
        getAllFilms()
        getAllDirectors()
        getAllGenres()
        getAllCountries()
    }, [])

    let addNewFilm = () => {
        axios.post('http://localhost:5555/catalog/api/v1/film/new_film', {
            id: 0,
            title: filmTitle,
            premierYear: filmDate,
            description: filmDescription,
            imageUrlLink: linkToCover,
            genre: postGenresList,
            country: postCountriesList,
            director: postDirectorList,
            rentPrice: filmRentPrice,
            salePrice: filmSalePrice

        }).then(response => console.log(response))
    }

    let changeDirectorsStateHandler = (event) => {
        console.log(event.target)
        if (!postDirectorList.includes(event.target.value)) {
            setPostDirectorList((prevState => prevState.concat([event.target.value])))
        }
    }
    let changeCountriesStateHandler = (event) => {
        console.log(event.target)
        if (!postCountriesList.includes(event.target.value)) {
            setPostCountriesList((prevState => prevState.concat([event.target.value])))
        }
    }
    let changeGenresStateHandler = (event) => {
        console.log(event.target)
        if (!postGenresList.includes(event.target.value)) {
            setPostGenresList((prevState => prevState.concat([event.target.value])))
        }
    }

    let clearPostDirectorList = () => {
        setPostDirectorList([])
    }
    let clearPostCountryList = () => {
        setPostCountriesList([])
    }
    let clearPostGenreList = () => {
        setPostGenresList([])
    }

    let addLinkToCover = (event) => {
        setLinkToCover(event.target.value)
    }

    function handleFilmsChange(event) {
        console.log(event.target.value)
        if (event.target.value === "Добавить фильм") {
            setOpen(true)
        } else {
            if (event.target.value !== "Добавить фильм") {
                setOpen(false)
                try {
                    return axios.get('http://localhost:5555/catalog/api/v1/film/id', {
                        params: {
                            id: event.target.value
                        }
                    }).then(response => setFile(response.data))
                        .then((data) => {
                        })
                } catch (e) {

                }
            }
        }
    }

    const [genres, setGenres] = useState([])
    const [directors, setDirectors] = useState([])
    const [countries, setCountries] = useState([])
    const [open, setOpen] = useState(true)
    const [openCountryMenu, setOpenCountryMenu] = useState(false)
    const [films, setFilms] = useState([])
    const [file, setFile] = useState([])
    const [linkToCover, setLinkToCover] = useState('')
    const [filmTitle, setFilmTitle] = useState('')
    const [filmDescription, setFilmDescription] = useState('')
    const [filmDate, setFilmDate] = useState(0)
    const [filmRentPrice, setFilmRentPrice] = useState(0)
    const [filmSalePrice, setFilmSalePrice] = useState(0)
    const [postDirectorList, setPostDirectorList] = useState([])
    const [postCountriesList, setPostCountriesList] = useState([])
    const [postGenresList, setPostGenresList] = useState([])
    const [checked, setChecked] = useState(false)


    function addFilmTitle(event) {
        setFilmTitle(event.target.value)
    }

    function addFilmDescription(event) {
        setFilmDescription(event.target.value)
    }

    function addPremierYear(event) {
        setFilmDate(event.target.value)
    }

    function addRentPrice(event) {
        setFilmRentPrice(event.target.value)
    }

    function addSalePrice(event) {
        setFilmSalePrice(event.target.value)
    }

    return (
        <div className={style.redactor_container}>
            <h3>
                Редактирование карточки фильма
            </h3>
            <div className={style.redactor__box}>
                <div className={style.redactor__img_container}>
                    <FormControl sx={{m: 1, minWidth: 120}} size="small" focused={false}>
                        <InputLabel id="demo-select-small-label">Фильмы</InputLabel>
                        <Select
                            labelId="demo-select-small-label"
                            id="demo-select-small"
                            name={'films'}
                            value={''}
                            color={'success'}
                            sx={{backgroundColor: 'white', borderColor: "success"}}
                            label="Age"
                            onChange={(event) => handleFilmsChange(event)}
                        >
                            <MenuItem value="Добавить фильм">
                                <em><PlusIcon/>Добавить фильм</em>
                            </MenuItem>
                            {films.map((film) =>
                                <MenuItem
                                    value={film.id}>{film.title}</MenuItem>
                            )}
                        </Select>
                    </FormControl>
                </div>
                {open ?
                    <div className={style.details}>
                        <div className={style.title_input}>
                            <div className={style.input_container}>
                                <input type="text" required="" placeholder={'Ссылка для обложки'}
                                       onChange={addLinkToCover}/>
                            </div>
                            <div className={style.input_container}>
                                <input type="text" required="" placeholder={'Название фильма'} onChange={addFilmTitle}/>
                            </div>
                        </div>
                        <div className={style.description_area}>
                            <textarea name="Text1" cols="40" rows="5" placeholder={'Содержание'}
                                      onChange={addFilmDescription}/>
                        </div>
                        <div className={style.details_box}>
                            <div className={style.input_container}>
                                <input type="number" required="" placeholder={'Год премьеры'}
                                       onChange={addPremierYear}/>
                            </div>
                            <div className={style.input_container}>
                                <input type="number" required="" placeholder={'Цена аренды'} onChange={addRentPrice}/>
                            </div>
                            <div className={style.input_container}>
                                <input type="number" required="" placeholder={'Цена продажи'} onChange={addSalePrice}/>
                            </div>
                        </div>

                        <div className={style.option_box}>
                            <div className={style.director_box}>
                                <span>Режиссёры</span>
                                <div className={style.board_box}>
                                    <div className={style.left_board}>
                                        <button className={style.clear_directors__btn}
                                                onClick={() => clearPostDirectorList()}>Очистить
                                        </button>
                                        {postDirectorList?.map((new_director) => <ListCardDeleteable
                                            msg={new_director}/>)}
                                    </div>
                                    <div className={style.right_board}>
                                        {directors?.map((director) => <ListCard
                                            changeStateHandler={(e) => changeDirectorsStateHandler(e)}
                                            checked={checked}
                                            msg={director.firstName + ' ' + director.lastName}/>)}
                                    </div>
                                </div>
                            </div>

                            <div className={style.director_box}>
                                <span>Страны</span>
                                <div className={style.board_box}>
                                    <div className={style.left_board}>
                                        <button className={style.clear_directors__btn}
                                                onClick={() => clearPostCountryList()}>Очистить
                                        </button>
                                        {postCountriesList?.map((new_country) => <ListCardDeleteable
                                            msg={new_country}/>)}
                                    </div>
                                    <div className={style.right_board}>
                                        {countries?.map((country) => <ListCard
                                            changeStateHandler={(e) => changeCountriesStateHandler(e)}
                                            checked={checked}
                                            msg={country.title}/>)}
                                    </div>
                                </div>
                            </div>
                            <div className={style.director_box}>
                                <span>Жанры</span>
                                <div className={style.board_box}>
                                    <div className={style.left_board}>
                                        <button className={style.clear_directors__btn}
                                                onClick={() => clearPostGenreList()}>Очистить
                                        </button>
                                        {postGenresList?.map((new_genre) => <ListCardDeleteable
                                            msg={new_genre}/>)}
                                    </div>
                                    <div className={style.right_board}>
                                        {genres?.map((genre) => <ListCard
                                            changeStateHandler={(e) => changeGenresStateHandler(e)}
                                            checked={checked}
                                            msg={genre.title}/>)}
                                    </div>
                                </div>
                            </div>

                        </div>
                        <div className={style.send_new_film__btn}>
                            <button onClick={() => addNewFilm()}>Сохранить</button>
                        </div>
                    </div>
                    :
                    <div className={style.details}>

                        <div className={style.details}>
                            <div className={style.title_input}>
                                <div className={style.input_container}>
                                    <input type="text" required="" value={file.imageUrlLink}/>
                                </div>
                                <div className={style.input_container}>
                                    <input type="text" required="" value={file.title}/>
                                </div>
                            </div>
                            <div className={style.description_area}>
                                <textarea name="Text1" cols="40" rows="5" value={file.description}/>
                            </div>
                            <div className={style.details_box}>
                                <div className={style.input_container}>
                                    <input type="text" required="" value={file.premierYear}/>
                                </div>
                                <div className={style.input_container}>
                                    <input type="text" required="" value={file.rentPrice}/>
                                </div>
                                <div className={style.input_container}>
                                    <input type="text" required="" value={file.salePrice}/>
                                </div>
                            </div>
                            <div className={style.select_box}>

                                <div className={style.country}>
                                    <div className={style.input_container}>
                                        <div className={style.director_box}>
                                            <span>Страны</span>
                                            <div className={style.board_box}>
                                                <div className={style.left_board}>
                                                    <button className={style.clear_directors__btn}
                                                            onClick={() => clearPostCountryList()}>Очистить
                                                    </button>
                                                    {file.country?.map((country) => <ListCardDeleteable
                                                        msg={country}/>)}
                                                </div>
                                                <div className={style.right_board}>
                                                    {countries?.map((country) => <ListCard
                                                        changeStateHandler={(e) => changeCountriesStateHandler(e)}
                                                        checked={checked}
                                                        msg={country.title}/>)}
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div className={style.director}>

                                    <div className={style.input_container}>
                                        <div className={style.director_box}>
                                            <span>Режиссёры</span>
                                            <div className={style.board_box}>
                                                <div className={style.left_board}>
                                                    <button className={style.clear_directors__btn}
                                                            onClick={() => clearPostDirectorList()}>Очистить
                                                    </button>
                                                    {file.director?.map((new_director) => <ListCardDeleteable
                                                        msg={new_director}/>)}
                                                </div>
                                                <div className={style.right_board}>
                                                    {directors?.map((director) => <ListCard
                                                        changeStateHandler={(e) => changeDirectorsStateHandler(e)}
                                                        checked={checked}
                                                        msg={director.firstName + ' ' + director.lastName}/>)}
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div className={style.director_box}>
                                    <span>Жанры</span>
                                    <div className={style.board_box}>
                                        <div className={style.left_board}>
                                            <button className={style.clear_directors__btn}
                                                    onClick={() => clearPostGenreList()}>Очистить
                                            </button>
                                            {file.genre?.map((new_genre) => <ListCardDeleteable
                                                msg={new_genre}/>)}
                                        </div>
                                        <div className={style.right_board}>
                                            {genres?.map((genre) => <ListCard
                                                changeStateHandler={(e) => changeGenresStateHandler(e)}
                                                checked={checked}
                                                msg={genre.title}/>)}
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                }
            </div>
        </div>
    )
}
export default RedactorPage