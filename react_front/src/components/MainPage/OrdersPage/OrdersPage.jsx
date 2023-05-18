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
            isSale: false
        }
    }

    componentDidMount() {
        this.getOrders()
    }


    getOrders = () => {
       try {
           axios.get('http://localhost:5555/cabinet/api/v1/orders')
               .then(response => response.data)
               .then(data => {
                   console.log(data)
                   this.setState({
                       films: data
                   })
               })
       }catch (e) {
           console.log(e)
       }

    }


    render() {
       let changeIsSaleState = () =>{
            if (this.state.isSale){
                this.setState({
                    isSale: true
                })
            } else{
                if (this.state.isSale){
                    this.setState({
                        isSale: false
                    })
                }
            }
        }
        return (
            <div className={'orders_container'}>

                {!this.state.isSale?
                    <div className={'rent_box__container'}>
                        <button className={'rent_box__btn'} onClick={() => changeIsSaleState()}>Купленные</button>
                        <span>Арендованные фильмы</span>
                        <div className={'rent_box'}>
                            {this.state.films.map(film =>
                                <RentCard  cover={film.imageUrlLink}
                                           title={film.filmTitle}
                                           description={film.description}
                                           price={film.price}
                                           rentStart={film.rentStart}
                                           rentEnd={film.rentEnd}
                                />
                            )}
                        </div>
                    </div>

                    :
                    <div className={'rent_box__container'}>
                        <button className={'rent_box__btn'} onClick={() => changeIsSaleState()}>Арендованные</button>
                        <span>Ваши фильмы</span>
                        <div className={'own_box'}>
                            {this.state.films.map(film =>
                                <OwnCard cover={film.imageUrlLink}
                                         title={film.filmTitle}
                                         description={film.description}
                                         price={film.price}/>
                            )}
                        </div>
                    </div>

                }


            </div>
        )
    }

}

export default OrdersPage;