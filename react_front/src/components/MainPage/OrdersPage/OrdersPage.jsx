import "./OrdersPage.css";
import axios from "axios";
import RentCard from "../../../widgets/RentCard/RentCard";
import OwnCard from "../../../widgets/OwnCard/OwnCard";
import {Component} from "react";

class OrdersPage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            films: [],
            saleFilms: [],
            rentFilms: [],
            isSale: false

        }
    }

    componentDidMount() {
        this.getRentedOrders()
        this.getSoldOrders()
    }


    getRentedOrders = () => {
        try {
            axios.get('http://localhost:5555/cabinet/api/v1/orders/rent')
                .then(response => response.data)
                .then(data => {
                    console.log(data)
                    this.setState({
                        rentFilms: data,
                    })
                }).catch(reason => console.log(reason))
        } catch (e) {
            console.log(e)
        }

    }
    getSoldOrders = () => {
        try {
            axios.get('http://localhost:5555/cabinet/api/v1/orders/sale')
                .then(response => response.data)
                .then(data => {
                    console.log(data)
                    this.setState({
                        saleFilms: data,
                    })
                })
        } catch (e) {
            console.log(e)
        }

    }


    render() {
        return (
            <div className={'orders_container'}>
                {this.state.saleFilms.map((each) => (
                    <div className={'rent_box__container'}>
                        <span>Арендованные фильмы</span>
                        <div className={'rent_box'}>
                            <RentCard cover={each.imageUrlLink}
                                      title={each.filmTitle}
                                      description={each.description}
                                      price={each.price}
                                      rentStart={each.rentStart}
                                      rentEnd={each.rentEnd}
                            />
                        </div>
                    </div>
                ))}


                {this.state.saleFilms.map(each =>
                    <div className={'rent_box__container'}>
                        <span>Ваши фильмы</span>
                        <div className={'own_box'}>
                            <OwnCard cover={each.imageUrlLink}
                                     title={each.filmTitle}
                                     description={each.description}
                                     price={each.price}/>
                        </div>
                    </div>
                )
                }
            </div>


        )
    }
}

export default OrdersPage;