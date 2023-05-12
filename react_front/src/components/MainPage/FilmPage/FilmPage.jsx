import "./FilmPage.css"
import {Icon} from "@mui/material";
import CurrencyRubleIcon from '@mui/icons-material/CurrencyRuble';
import axios from "axios";


function FilmPage(props) {


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
    let addToCart = async () => {
        try {
            const response = await axios.get('http://localhost:5555/cart/api/v1/cart/' + localStorage.getItem('cartId') + '/add/', {
                params: {
                    filmId: props.filmId,
                    filmTitle: props.title,
                    filmImgUrlLink: props.cover,
                    filmPrice: getPrice(),
                    isSale: props.isSale
                }
            })
            console.log("Ответ метода addToCart: " + response.data)
        } catch (e) {
            console.log("Ошибка в методе addToCart(): " + e)
        }


    }
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
                    <div className="movie__details">
                        <h4>Режиссёр: </h4>
                        {props.director.map((director) =>
                            <p className="movie__detail"><span className="icons icons-red">
                            </span>{director}</p>
                        )}

                        <p className="movie__detail"><span className="icons icons-grey"><i className="fas fa-clock"></i> </span>{props.premierYear}
                        </p>
                        <p className="movie__detail"><span className="icons icons-grey"><i className="fas fa-clock"></i> </span>{props.country}
                        </p>

                    </div>
                    <p className="movie__detail"><span className="icons icons-yellow"><i
                        className="fas fa-file-invoice-dollar"></i>
                    </span>
                        <button className={props.isSale ? 'pay_btn' : 'pay_btn sale'}
                                onClick={() => addToCart()}

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
        </div>
    )
}

export default FilmPage;