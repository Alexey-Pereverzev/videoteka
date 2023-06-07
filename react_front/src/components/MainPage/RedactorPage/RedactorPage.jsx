import style from "./RedactorPage.module.css"
import React, {createRef, useEffect, useRef, useState} from "react";
import {Button, ButtonGroup, FormControl, InputLabel, MenuItem, Select} from "@mui/material";
import {SelectButton} from "primereact/selectbutton";
import {SelectButtonChangeEvent} from "primereact/selectbutton";
import axios from "axios";
import {PlusIcon} from "primereact/icons/plus";
import {toast} from "react-toastify";
import CatalogPage from "../CatalogPage/CatalogPage";

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
    interface Title {
        id: number;
        title: string;
    }


    let getGenres = () => {
        axios.get("http://localhost:5555/catalog/api/v1/genre/all")
            .then(response => response.data)
            .then((data) => {
                console.log(data)
                setGenres(data)
            }).catch((error) => {
            console.error("Error: " + error)
        })
    }
    useEffect(() => {
        getAllFilms()
    }, [])

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

    function handleFilmsChange(event) {
        console.log(event.target.value)
        if (event.target.value === "Добавить фильм") {
            setOpen(true)
        } else {
            if (event.target.value !== "Добавить фильм") {
                setOpen(false)
            }
        }
    }

    const [genres, setGenres] = useState([])
    const [value, setValue] = useState([])
    const [directors, setDirectors] = useState([])
    const [countries, setCountries] = useState([])
    const [open, setOpen] = useState(false)
    const [films, setFilms] = useState([])

    return (
        <div className={style.redactor_container}>
            Редактирование карточки фильма
            <div className={style.redactor__box}>
                <div className={style.redactor__img_container}>
                    <FormControl sx={{m: 1, minWidth: 120}} size="small" focused={false}>
                        <InputLabel id="demo-select-small-label">Фильмы</InputLabel>
                        <Select
                            labelId="demo-select-small-label"
                            id="demo-select-small"
                            name={'director'}
                            value={films}
                            color={'success'}
                            defaultValue={''}
                            sx={{backgroundColor: 'darkgrey', borderColor: "success"}}
                            label="Age"
                            onChange={(event) => handleFilmsChange(event)}
                        >
                            <MenuItem value="Добавить фильм">
                                <em><PlusIcon/>Добавить фильм</em>
                            </MenuItem>
                            {films.map((film) =>
                                <MenuItem
                                    value={film.title}>{film.title}</MenuItem>
                            )}
                        </Select>
                    </FormControl>
                </div>
                {open ?
                    <div className={style.details}>
                        <div className={style.title_input}>
                            <div className={style.input_container}>
                                <input type="text" required=""/>
                                <label>Ссылка для обложки</label>
                            </div>
                            <div className={style.input_container}>
                                <input type="text" required=""/>
                                <label>Название фильма</label>
                            </div>
                        </div>
                        <div className={style.description_area}>
                            <textarea name="Text1" cols="40" rows="5"/>
                            <label>Содержание</label>
                        </div>
                        <div className={style.details_box}>
                            <div className={style.input_container}>
                                <input type="text" required=""/>
                                <label>Год премьеры</label>
                            </div>
                            <div className={style.input_container}>
                                <input type="text" required=""/>
                                <label>Цена аренды</label>
                            </div>
                            <div className={style.input_container}>
                                <input type="text" required=""/>
                                <label>Цена продажи</label>
                            </div>
                        </div>
                        <div className={style.country}>
                            <div className={style.input_container}>
                                <input onChange={(event) => setDirectors(event.target.value)}
                                       type="text"
                                       required=""/>
                                <label>Страны, через запятую</label>
                            </div>
                        </div>
                    </div>
                    :
                    <div className={style.details}>
                        {films ?
                            <div>
                                {films.map((film) => (
                                    <div className={style.details}>
                                        <div className={style.title_input}>
                                            <div className={style.input_container}>
                                                <input type="text" required=""/>
                                                <label>{film.imageUrlLink}</label>
                                            </div>
                                            <div className={style.input_container}>
                                                <input type="text" required=""/>
                                                <label>{film.title}</label>
                                            </div>
                                        </div>
                                        <div className={style.description_area}>
                                            <textarea name="Text1" cols="40" rows="5"/>
                                            <label>{film.description}</label>
                                        </div>
                                        <div className={style.details_box}>
                                            <div className={style.input_container}>
                                                <input type="text" required=""/>
                                                <label>{film.premierYear}</label>
                                            </div>
                                            <div className={style.input_container}>
                                                <input type="text" required=""/>
                                                <label>{film.rentPrice}</label>
                                            </div>
                                            <div className={style.input_container}>
                                                <input type="text" required=""/>
                                                <label>{film.salePrice}</label>
                                            </div>
                                        </div>
                                        <div className={style.country}>
                                            {countries.map((country) => (
                                                <div className={style.input_container}>
                                                    <input onChange={(event) => setDirectors(event.target.value)}
                                                           type="text"
                                                           required=""/>
                                                    <label>{country.title}</label>
                                                </div>
                                            ))}
                                        </div>
                                        <div className={style.director}>
                                            {directors.map((director) => (
                                                <div className={style.input_container}>
                                                    <input type="text" required=""/>
                                                    <label>{director}</label>
                                                </div>
                                            ))}
                                        </div>
                                    </div>
                                ))
                                }
                            </div>
                            :
                            <div>Нет такого фильма</div>
                        }
                    </div>
                }
            </div>
        </div>
    )
}
export default RedactorPage