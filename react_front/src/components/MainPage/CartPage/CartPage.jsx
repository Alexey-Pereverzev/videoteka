import "./CartPage.css"
import {NavLink} from "react-router-dom";
import {Icon} from "@mui/material";
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import StringCard from "../../../widgets/StringCard/StringCard";
import {Component} from "react";

class CartPage extends Component{
    constructor(props) {
        super(props);
        this.state = {
            cartItems:[
                {
                price:'107',
                pricePerFilm: '107',
                title:'Жара',
                id:'1',
                imageUrlLink:'https://media.filmz.ru/photos/full/filmz.ru_f_27614.jpg',
                quantity: '3'
            }, {
                    price:'107',
                    pricePerFilm: '107',
                    title:'Жара',
                    id:'1',
                    imageUrlLink:'https://media.filmz.ru/photos/full/filmz.ru_f_27614.jpg',
                    quantity: '3'
                }, {
                    price:'107',
                    pricePerFilm: '107',
                    title:'Жара',
                    id:'1',
                    imageUrlLink:'https://media.filmz.ru/photos/full/filmz.ru_f_27614.jpg',
                    quantity: '3'
                },{
                    price:'107',
                    pricePerFilm: '107',
                    title:'Жара',
                    id:'1',
                    imageUrlLink:'https://media.filmz.ru/photos/full/filmz.ru_f_27614.jpg',
                    quantity: '3'
                },{
                    price:'107',
                    pricePerFilm: '107',
                    title:'Жара',
                    id:'1',
                    imageUrlLink:'https://media.filmz.ru/photos/full/filmz.ru_f_27614.jpg',
                    quantity: '3'
                },{
                    price:'107',
                    pricePerFilm: '107',
                    title:'Жара',
                    id:'1',
                    imageUrlLink:'https://media.filmz.ru/photos/full/filmz.ru_f_27614.jpg',
                    quantity: '3'
                },{
                    price:'107',
                    pricePerFilm: '107',
                    title:'Жара',
                    id:'1',
                    imageUrlLink:'https://media.filmz.ru/photos/full/filmz.ru_f_27614.jpg',
                    quantity: '3'
                },{
                    price:'107',
                    pricePerFilm: '107',
                    title:'Жара',
                    id:'1',
                    imageUrlLink:'https://media.filmz.ru/photos/full/filmz.ru_f_27614.jpg',
                    quantity: '3'
                },
            ],
        }
    }
    render() {
        return (
            <div className={'cart_container'}>
                <div className={'cart_container__plate'}>
                    <div className={'header'}>
                        <NavLink to={'/'}>
                            <span className={'to_catalog'}><ArrowBackIcon/><h4>Выбрать новые фильмы</h4></span>
                        </NavLink>
                        <div className={'delimiter'}>
                            <span>.</span>
                        </div>
                        <div className={'details'}>
                            <span>У вас в корзине 4 ед. товара</span>
                        </div>
                    </div>

                    <div className={'card_box'}>
                        {this.state.cartItems.map((item) =>
                                <StringCard quantity={item.quantity}
                                            title={item.title}
                                            price={item.price}
                                            cover={item.imageUrlLink}
                                />
                            )
                        }

                    </div>
                    <div className={'checkout_box'}></div>
                </div>
            </div>
        )
    }
}

export default CartPage;