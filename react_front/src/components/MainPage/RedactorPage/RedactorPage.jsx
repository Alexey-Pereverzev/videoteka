import style from "./RedactorPage.module.css"
import React, {createRef, useEffect, useRef, useState} from "react";
import {Button, ButtonGroup} from "@mui/material";
import {SelectButton} from "primereact/selectbutton";
import {SelectButtonChangeEvent} from "primereact/selectbutton";
import axios from "axios";

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
        axios.get("http://localhost:5555/catalog/api/v1/genre/list_all")
            .then(response => response.data)
            .then((data) => {
                console.log(data)
                setGenres(data)
            }).catch((error) => {
            console.error("Error: " + error)
        })
    }
    useEffect(() => {
        getGenres()
    },[])


    const [genres, setGenres] = useState([])
    const [value, setValue] = useState([])
    const [directors, setDirectors] = useState([])
    const [countries, setCountries] = useState([])

    return (
        <div className={style.redactor_container}>
            Редактирование карточки фильма
            <div className={style.redactor__box}>
                <div className={style.redactor__img_container}>
                    <div className={style.input_container}>
                        <input type="text" required=""/>
                        <label>Ссылка для обложки</label>
                    </div>
                </div>
                <div className={style.details}>
                    <div className={style.title_input}>
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
                        {directors.map(director => {
                            return(
                                    <div className={style.input_container}>
                                        <input onChange={(event) => setDirectors(event.target.value)}
                                               value={director}
                                               type="text"
                                               required=""/>
                                        <label>Страны, через запятую</label>
                                    </div>
                                )
                        })}

                    </div>
                    <div className={style.director}>
                        <div className={style.input_container}>
                            <input type="text" required=""/>
                            <label>Режиссёры, через запятую</label>
                        </div>
                    </div>
                </div>
                <div className={style.genre_box}>
                        <SelectButton value={value}
                                      onChange={(e: SelectButtonChangeEvent) => setValue(e.title)}
                                      optionLabel="title"
                                      options={genres}
                                      multiple/>

                </div>
            </div>
        </div>
    )
}
export default RedactorPage