import style from "./RedactorPage.module.css"
import React, {createRef, useRef, useState} from "react";
import {Button, ButtonGroup} from "@mui/material";

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

  let  toggleOption = (e) => {
        const key = e.target.value; // e.g. 'A'
        const value = !selected[key];
        const newSelected = Object.assign(selected, {[key]: value});
        setSelected({ selected: newSelected });
        console.log('this.state', selected);
    }
   let getBsStyle = (key) => {
        return selected[key] ? 'primary' : 'error';
    }
const[selected, setSelected] = useState([{
    'A': false,
    'B': false,
    'C': false
}])
    return(
        <div className={style.redactor_container}>
            Редактирование карточки фильма
            <div className={style.redactor__box}>
                <div className={style.redactor__img_container}>
                    <div  className={style.input_container}>
                        <input  type="text" required=""/>
                        <label>Ссылка для обложки</label>
                    </div>
                </div>
                <div className={style.details}>
                    <div className={style.title_input}>
                        <div  className={style.input_container}>
                            <input  type="text" required=""/>
                            <label>Название фильма</label>
                        </div>
                    </div>
                    <div className={style.description_area}>
                        <textarea name="Text1" cols="40" rows="5"/>
                        <label>Содержание</label>
                    </div>
                    <div className={style.details_box}>
                        <div  className={style.input_container}>
                            <input  type="text" required=""/>
                            <label>Год премьеры</label>
                        </div>
                        <div  className={style.input_container}>
                            <input  type="text" required=""/>
                            <label>Цена аренды</label>
                        </div>
                        <div  className={style.input_container}>
                            <input  type="text" required=""/>
                            <label>Цена продажи</label>
                        </div>
                    </div>
                    <div className={style.country}>
                        <div  className={style.input_container}>
                            <input  type="text" required=""/>
                            <label>Страны, через запятую</label>
                        </div>
                    </div>
                    <div className={style.director}>
                        <div  className={style.input_container}>
                            <input  type="text" required=""/>
                            <label>Режиссёры, через запятую</label>
                        </div>
                    </div>
                </div>
                <div className={style.genre_box}>
                    <ButtonGroup>
                        <Button onClick={toggleOption} value='A' bsStyle={getBsStyle('A')}>
                            Option A
                        </Button>
                        <Button onClick={toggleOption} value='B' bsStyle={getBsStyle('B')}>
                            Option B
                        </Button>
                        <Button onClick={toggleOption} value='C' bsStyle={getBsStyle('C')}>
                            Option C
                        </Button>
                    </ButtonGroup>
                </div>
            </div>
        </div>
    )
}
export default RedactorPage