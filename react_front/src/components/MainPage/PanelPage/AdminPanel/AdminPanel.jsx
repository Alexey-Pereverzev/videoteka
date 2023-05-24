import "./AdminPanel.css";
import {Avatar} from "@mui/material";
import axios from "axios";
import {NavLink, Route, Routes} from "react-router-dom";
import ProfilePage from "../../ProfilePage/ProfilePage";
import OrdersPage from "../../OrdersPage/OrdersPage";
import FavoritesPage from "../../FavoritesPage/FavoritesPage";
import UsersPage from "../../UsersPage/UsersPage";

function AdminPanel() {
    // let getUsers = () => {
    //     let userId = JSON.parse(localStorage.getItem('userId'))
    //     console.log(userId)
    //     // axios.get('http://localhost:5555/auth/api/v1/users')
    //     //     .then(response => response.data)
    //     //     .then(data => console.log(data))
    // }
    // getUsers()
    return(
        <div className={'admin_container'}>
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
                <div className={'menu__item'}>
                    <NavLink to={'users'}>Пользователи</NavLink>
                </div>
            </div>
            <div>
                <div>
                    <Routes>
                        <Route path={'profile'} element={<ProfilePage/>}/>
                        <Route path={'orders'} element={<OrdersPage/>}/>
                        <Route path={'favourites'} element={<FavoritesPage/>}/>
                        <Route path={'users'} element={<UsersPage/>}/>
                    </Routes>
                </div>
            </div>
        </div>
    )
}

export default AdminPanel;