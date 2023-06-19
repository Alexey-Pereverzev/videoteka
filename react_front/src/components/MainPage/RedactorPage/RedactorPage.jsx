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

    let getAllDirectors = () => {
        console.log("Вошли в метод getAllDirectors")
        setOpenDirectorMenu(true)
        axios.get('http://localhost:5555/catalog/api/v1/director/all')
            .then(r => r.data)
            .then(data => {
                setDirectors(data)
            })
    }
    let handleDirectorsChange = () => {

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
    const [value, setValue] = useState([])
    const [directors, setDirectors] = useState([])
    const [countries, setCountries] = useState([])
    const [open, setOpen] = useState(true)
    const [openCountryMenu, setOpenCountryMenu] = useState(false)
    const [openDirectorMenu, setOpenDirectorMenu] = useState(false)
    const [openGenreMenu, setOpenGenreMenu] = useState(false)
    const [films, setFilms] = useState([])
    const [file, setFile] = useState([])
    console.log(file)

    function saveDirector() {

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
                                <input type="text"
                                       required=""/>
                                <label>Страны, через запятую</label>
                            </div>
                        </div>
                        <div className={style.director}>
                            <div className={style.input_container}>
                                <input type="text"
                                       required=""/>
                                <label>Режисёр, через запятую</label>
                            </div>
                        </div>
                    </div>
                    :
                    // file.map((detail) => (

                    <div className={style.details}>

                        <div className={style.details}>
                            <div className={style.title_input}>
                                <div className={style.input_container}>
                                    <input type="text" required=""/>
                                    <label>{file.imageUrlLink}</label>
                                </div>
                                <div className={style.input_container}>
                                    <input type="text" required=""/>
                                    <label>{file.title}</label>
                                </div>
                            </div>
                            <div className={style.description_area}>
                                <textarea name="Text1" cols="40" rows="5" value={file.description}/>
                            </div>
                            <div className={style.details_box}>
                                <div className={style.input_container}>
                                    <input type="text" required=""/>
                                    <label>{file.premierYear}</label>
                                </div>
                                <div className={style.input_container}>
                                    <input type="text" required=""/>
                                    <label>{file.rentPrice}</label>
                                </div>
                                <div className={style.input_container}>
                                    <input type="text" required=""/>
                                    <label>{file.salePrice}</label>
                                </div>
                            </div>
                            <div className={style.select_box}>

                                <div className={style.country}>
                                    <div className={style.input_container}>
                                        <FormControl sx={{m: 1, minWidth: 120}} size="small" focused={false}>
                                            <InputLabel id="demo-select-small-label">Страны</InputLabel>
                                            <Select
                                                labelId="demo-select-small-label"
                                                id="demo-select-small"
                                                name={'films'}
                                                value={''}
                                                color={'success'}
                                                sx={{
                                                    backgroundColor: '#2b303b',
                                                    borderColor: '#c5c5c5',
                                                    outlineColor: '#c5c5c5',
                                                    color: '#c5c5c5'
                                                }}
                                                label="Age"
                                                onChange={(event) => handleFilmsChange(event)}
                                            >
                                                <MenuItem value="Добавить страну">
                                                    <button className={style.redactor_btn}
                                                            onClick={() => setOpenCountryMenu(true)}><PlusIcon/><label>Добавить
                                                        страну</label></button>
                                                </MenuItem>
                                                {file.country?.map((country) => <MenuItem>{country}</MenuItem>)}
                                            </Select>
                                        </FormControl>
                                    </div>

                                </div>

                                <div className={style.director}>

                                    <div className={style.input_container}>
                                        <FormControl sx={{m: 1, minWidth: 120}} size="small" focused={false}>
                                            <InputLabel id="demo-select-small-label">Режиссёры</InputLabel>
                                            <Select
                                                labelId="demo-select-small-label"
                                                id="demo-select-small"
                                                name={'films'}
                                                value={''}
                                                color={'success'}
                                                sx={{
                                                    backgroundColor: '#2b303b',
                                                    borderColor: '#c5c5c5',
                                                    outlineColor: '#c5c5c5',
                                                    color: '#c5c5c5'
                                                }}
                                                label="Age"
                                                // onChange={(event) => handleFilmsChange(event)}
                                            >
                                                <MenuItem value="Добавить режисёра">
                                                    <button className={style.redactor_btn}
                                                            onClick={() => getAllDirectors()}><PlusIcon/><label>Добавить
                                                        режиссёра</label></button>
                                                </MenuItem>
                                                {file.director?.map((director) => <MenuItem>{director}</MenuItem>)}
                                                {openDirectorMenu ?
                                                    <Dialog disableEscapeKeyDown open={openDirectorMenu}
                                                            onClose={() => setOpenDirectorMenu(false)}>
                                                        <DialogTitle>Выберайте режиссёра</DialogTitle>
                                                        <DialogContent>
                                                            <Box component="form"
                                                                 sx={{display: 'flex', flexWrap: 'wrap'}}>
                                                                <Select
                                                                    labelId="demo-select-small-label"
                                                                    id="demo-select-small"
                                                                    name={'films'}
                                                                    value={directors}
                                                                    multiple
                                                                    color={'success'}
                                                                    sx={{
                                                                        width: 100,
                                                                        backgroundColor: 'darkgrey',
                                                                        borderColor: "success"
                                                                    }}
                                                                    label="Age"
                                                                    onChange={(event) => handleDirectorsChange(event)}
                                                                >
                                                                    {directors.map((director) =>
                                                                        <MenuItem
                                                                            key={director.id}>{director.firstName + ' ' + director.lastName}</MenuItem>)}
                                                                </Select>
                                                            </Box>
                                                        </DialogContent>
                                                        <DialogActions>
                                                            <Button
                                                                onClick={() => setOpenDirectorMenu(false)}>Отменить</Button>
                                                            <Button onClick={() => saveDirector()}>Сохранить</Button>
                                                        </DialogActions>
                                                    </Dialog>
                                                    :
                                                    null
                                                }
                                            </Select>
                                        </FormControl>
                                    </div>
                                </div>
                                <div className={style.genre_container}>
                                    <div className={style.input_container}>
                                        <FormControl sx={{m: 1, minWidth: 120}} size="small" focused={false}>
                                            <InputLabel id="demo-select-small-label">Жанры</InputLabel>
                                            <Select
                                                labelId="demo-select-small-label"
                                                id="demo-select-small"
                                                name={'films'}
                                                value={''}
                                                color={'success'}
                                                sx={{
                                                    backgroundColor: '#2b303b',
                                                    borderColor: '#c5c5c5',
                                                    outlineColor: '#c5c5c5',
                                                    color: '#c5c5c5'
                                                }}
                                                label="Age"

                                            >
                                                <MenuItem value="Добавить режисёра">
                                                    <button className={style.redactor_btn}
                                                            onClick={() => getAllDirectors()}><PlusIcon/><label>Добавить
                                                        жанр</label></button>
                                                </MenuItem>
                                                {file.genre?.map((director) => <MenuItem>{director}</MenuItem>)}
                                                {openGenreMenu ?
                                                    <Dialog open={open}
                                                            onClose={() => setOpenDirectorMenu(false)}>
                                                        <DialogTitle>Выберайте режиссёра</DialogTitle>
                                                        <DialogContent>
                                                            <Box component="form"
                                                                 sx={{display: 'flex', flexWrap: 'wrap'}}>
                                                                <Select
                                                                    labelId="demo-select-small-label"
                                                                    id="demo-select-small"
                                                                    name={'films'}
                                                                    value={directors}
                                                                    multiple
                                                                    color={'success'}
                                                                    sx={{
                                                                        backgroundColor: 'darkgrey',
                                                                        borderColor: "success"
                                                                    }}
                                                                    label="Age"
                                                                    onChange={(event) => handleDirectorsChange(event)}
                                                                >
                                                                    {directors.map((director) =>
                                                                        <MenuItem>{director}</MenuItem>)}
                                                                </Select>
                                                            </Box>
                                                        </DialogContent>
                                                        <DialogActions>
                                                            <Button
                                                                onClick={() => setOpenDirectorMenu(false)}>Отменить</Button>
                                                            <Button onClick={() => saveDirector()}>Сохранить</Button>
                                                        </DialogActions>
                                                    </Dialog>
                                                    :
                                                    null
                                                }
                                            </Select>
                                        </FormControl>
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