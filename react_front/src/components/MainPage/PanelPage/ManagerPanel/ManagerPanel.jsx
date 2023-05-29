import "./ManagerPanel.css";
import {Avatar} from "@mui/material";
import axios from "axios";
import {NavLink, Route, Routes} from "react-router-dom";
import ProfilePage from "../../ProfilePage/ProfilePage";
import OrdersPage from "../../OrdersPage/OrdersPage";
import FavoritesPage from "../../FavoritesPage/FavoritesPage";
import UsersPage from "../../UsersPage/UsersPage";
import RedactorPage from "../../RedactorPage/RedactorPage";
import ModeratorPage from "../../ModeratorPage/ModeratorPage";

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
                        {localStorage.getItem('fullName')}
                    </span>
                </div>
                <div className={'menu__item'}>
                    <NavLink to={'profile'}>
                        Профиль
                    </NavLink>
                </div>
                <div className={'menu__item'}>
                    <NavLink to={'/cart'}>
                        Корзина
                    </NavLink>
                </div>
                <div className={'menu__item'}>
                    <NavLink to={'orders'}>Мои фильмы</NavLink>
                </div>
                <div className={'menu__item'}>
                    <NavLink to={'favourites'}>Избранное</NavLink>
                </div>
                <div className={'menu__item'}>
                    <NavLink to={'redactor'}>Редактор карточки фильма</NavLink>
                </div>
                <div className={'menu__item'}>
                    <NavLink to={'moderation'}>Редактор отзывов</NavLink>
                </div>

            </div>
            <div>
                <Routes>
                    <Route path={'profile'} element={<ProfilePage/>}/>
                    <Route path={'orders'} element={<OrdersPage/>}/>
                    <Route path={'favourites'} element={<FavoritesPage/>}/>
                    <Route path={'redactor'} element={<RedactorPage/>}/>
                    <Route path={'moderation'} element={<ModeratorPage/>}/>
                </Routes>
            </div>
        </div>
    )
}

export default ManagerPanel;