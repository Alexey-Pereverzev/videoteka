import "./OrdersPage.css";
import axios from "axios";
import RentCard from "../../../widgets/RentCard/RentCard";
import OwnCard from "../../../widgets/OwnCard/OwnCard";
import {Component} from "react";

class OrdersPage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            rentFilms: [],
            soldFilms: []
        }
    }

    getOrders = () => {
        axios.get('http://localhost:5555/cabinet/api/v1/orders')
            .then(response => response.data)
            .then(data => {
                console.log(data)
            })
    }

    render() {
        this.getOrders()
        return (
            <div className={'orders_container'}>

                    <div className={'rent_box'}>

                            <RentCard />


                    </div>

                    <div className={'own_box'}>
                        <OwnCard/>
                    </div>

            </div>
        )
    }

}

export default OrdersPage;