import "./ManagerPanel.css";
import {Avatar} from "@mui/material";
import axios from "axios";

function ManagerPanel() {
    let getOrders = () => {
        let userId = JSON.parse(localStorage.getItem('userId'))
        console.log(userId)
        axios.get('http://localhost:5555/cabinet/api/v1/orders/'+ userId )
            .then(response => response.data)
            .then(data => console.log(data))
    }

    function getBasket() {
        console.log('Корзина')
    }

    function getProfile() {
        console.log('Профиль')
    }

    function getFavourites() {
        console.log('Избранное')
    }
    return(
        <div className={'manager_container'}>
            <div className={'main_menu'}>
                <div className={'menu__info'}>
                    <Avatar/>
                    <span>
                        {JSON.parse(localStorage.getItem('username'))}
                    </span>
                </div>
                <div className={'menu__item'}>
                    <button onClick={() => getProfile()}>Профиль</button>
                </div>
                <div className={'menu__item'}>
                    <button onClick={() => getBasket()}>Корзина</button>
                </div>
                <div className={'menu__item'}>
                    <button onClick={() => getOrders()}>Мои фильмы</button>
                </div>
                <div className={'menu__item'}>
                    <button  onClick={() => getFavourites()}>Избранное</button>
                </div>
            </div>
            <div></div>
        </div>
    )
}

export default ManagerPanel;