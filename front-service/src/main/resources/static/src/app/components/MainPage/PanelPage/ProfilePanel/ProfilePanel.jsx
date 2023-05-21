import "./ProfilePanel.css";
import {Avatar} from "@mui/material";
import {NavLink, Route, Routes} from "react-router-dom";
import ProfilePage from "../../ProfilePage/ProfilePage";
import OrdersPage from "../../OrdersPage/OrdersPage";
import FavoritesPage from "../../FavoritesPage/FavoritesPage";
import {useState} from "react";

function ProfilePanel() {

    const [link, setLink] = useState(null)

    function getBasket() {
        console.log('Корзина')
    }

    function getProfile() {
        setLink('profile')
        console.log('Профиль')
    }

    function getFavourites() {
        console.log('Избранное')
    }

    return (
        <div className={'profile_container'}>
            <div className={'main_menu'}>
                <div className={'menu__info'}>
                    <Avatar/>
                    <span>
                        {JSON.parse(localStorage.getItem('username'))}
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
            </div>
            <div>
                <Routes>
                    <Route path={'profile'} element={<ProfilePage/>}/>
                    <Route path={'orders'} element={<OrdersPage/>}/>
                    <Route path={'favourites'} element={<FavoritesPage/>}/>
                </Routes>
            </div>
        </div>
    )
}

export default ProfilePanel;