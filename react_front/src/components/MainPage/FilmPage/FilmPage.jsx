import "./FilmPage.css"
import "react-toastify/dist/ReactToastify.css";
import {Icon, Rating} from "@mui/material";
import CurrencyRubleIcon from '@mui/icons-material/CurrencyRuble';
import axios from "axios";
import {toast, ToastContainer} from "react-toastify";
import React, {useState} from "react";
import StarBorderOutlinedIcon from "@mui/icons-material/StarBorderOutlined";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import {NavLink} from "react-router-dom";


function FilmPage(props) {


    let displayCartNotification = (message) => {
        toast.success(message);
    };
    let getFilmIdRating = () => {
        // const userId = JSON.parse(localStorage.getItem("userId"))
        try {
            axios.get('http://localhost:5555/catalog/api/v1/rating/grade_user_by_id_film', {
                params: {
                    filmId: props.filmId
                }
            })
                .then(response => response.data)
                .then(data => {
                        setDisable(true)
                        console.log(data)
                        setOldRatingState(data.grade)
                    }
                )

        } catch (e) {

        }
    }
    let setValue = (newValue) => {
        const userId = JSON.parse(localStorage.getItem("userId"))
        try {
            axios.post('http://localhost:5555/catalog/api/v1/rating/add_new',
                {
                    film_id: props.filmId,
                    user_id: Number(userId),
                    grade: newValue,
                }
            ).then(r => r.data)
                .then(() => getFilmIdRating())

        } catch (e) {

        }

    }
    let getPrice = () => {
        let price = ''
        if (props.isSale) {
            price = props.salePrice
        } else {
            if (!props.isSale) {
                price = props.rentPrice
            }
        }
        return price
    }
    let addToCart = async (sale) => {
        const price = getPrice()
        const stringCover = props.cover
        if (localStorage.getItem("customer")) {
            try {
                const response = await axios.get('http://localhost:5555/cart/api/v1/cart/add',
                    {
                        params: {
                            uuid: localStorage.getItem('guestCartId'),
                            filmId: props.filmId,
                            filmTitle: props.title,
                            filmPrice: price,
                            filmImageUrlLink: stringCover,
                            isSale: sale
                        }
                    }
                )
                    displayCartNotification(response.data.value)
                console.log("Ответ метода addToCart: " + response.data)
            } catch (e) {
                alert(e)
            }
        } else {
            displayCartNotification('Добавление в корзину и оформление заказов возможно только для авторизованных пользователей! Пожалуйста, авторизуйтесь!')
        }

    }
    const handleChange = () => {
        props.setCommand('reviews') // callback-функция
    }
    const [oldRatingState, setOldRatingState] = useState(0.00)
    const [disable, setDisable] = useState(false)
    getFilmIdRating()

    return (
        <div className={'film-page'}>
            <div className="movie">
                <div className="movie__hero">
                    <img
                        src={props.cover}
                        alt={props.title} className="movie__img"/>
                </div>
                <div className="movie__content">
                    <div className="movie__title">
                        <h1 className="heading__primary">{props.title}<i className="fas fa-fire"></i></h1>
                    </div>
                    <div className={'movie__genre'}>
                        {props.genre.map((genre) => <div className="movie__tag movie__tag--1">#{genre}</div>)}
                    </div>

                    <p className="movie__description">
                        {props.description}
                    </p>
                    <div>
                        {disable ?
                            <Rating
                                name="read-only"
                                value={oldRatingState}
                                emptyIcon={<StarBorderOutlinedIcon style={{opacity: 0.55, color: 'white'}}
                                                                   fontSize="inherit"/>}
                                readOnly
                            />
                            :
                            <Rating
                                name="simple-controlled"
                                value={oldRatingState}
                                emptyIcon={<StarBorderOutlinedIcon style={{opacity: 0.55, color: 'white'}}
                                                                   fontSize="inherit"/>}
                                onChange={(event, newValue) => {
                                    setValue(newValue);
                                }}
                            />

                        }

                    </div>
                    <div className="movie__details">
                        <h4>Режиссёр: </h4>
                        {props.director.map((director) =>
                            <p className="movie__detail"><span className="icons icons-red">
                            </span>{director}</p>
                        )}

                        <p className="movie__detail"><span className="icons icons-grey"><i
                            className="fas fa-clock"></i> </span>{props.premierYear}
                        </p>
                        {props.country.map((country) =>
                            <p className="movie__detail"><span className="icons icons-grey">
                                <i className="fas fa-clock"></i> </span>{country}
                            </p>
                        )}
                    </div>
                    <div className={'reviews_link'}>
                        <button onClick={() => handleChange()}>
                            <span className={'to_reviews'}><h4>Отзывы на фильм</h4></span>
                        </button>
                    </div>
                    <p className="movie__detail"><span className="icons icons-yellow"><i
                        className="fas fa-file-invoice-dollar"></i>
                    </span>
                        <button className={props.isSale ? 'pay_btn' : 'pay_btn sale'}
                                onClick={() => addToCart(props.isSale)}

                        >В Корзину
                        </button>
                    </p>
                </div>

                {props.isSale ?
                    <div className="movie__price">
                        Цена продажи: {props.salePrice}<Icon sx={{transform: 'rotate(90deg)'}}
                                                             component={CurrencyRubleIcon}/>
                    </div>
                    :
                    <div className="movie__price">
                        Цена аренды: {props.rentPrice}<Icon sx={{transform: 'rotate(90deg)'}}
                                                            component={CurrencyRubleIcon}/>
                    </div>
                }
            </div>
            <ToastContainer
                position="top-right"
                autoClose={5000}
                hideProgressBar={false}
                newestOnTop={false}
                closeOnClick
                rtl={false}
                pauseOnFocusLoss
                draggable
                pauseOnHover
                theme="dark"
            />
        </div>
    )
}

export default FilmPage;