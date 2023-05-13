import {NavLink} from "react-router-dom";
import {Avatar} from "@mui/material";
import SearchBar from "../../../widgets/SearchBar/SearchBar";
import DropdownItem from "../../../widgets/DropdownItem/DropdownItem";
import {useEffect, useRef, useState} from "react";
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart'
import "./Header.css"


function Header(props) {
    let username = JSON.parse(localStorage.getItem('username'))

    let getCurrentUser = () => {
        return JSON.parse(localStorage.getItem('customer'))
    }

    let user = getCurrentUser()


    let logout = () => {
        localStorage.removeItem("customer")
        localStorage.removeItem("username")
        user = null
        window.location = '/'
    }
    let loginRedirector = () => {
        window.location = "/login"
    }
    let openMenu = () => {
        setOpen(!open)
    }

    const [open, setOpen] = useState(false);

    let menuRef = useRef();
    useEffect(() => {
        if (user){
            let handler = (event) => {
                if (!menuRef.current.contains(event.target)) {
                    setOpen(false);
                }
            }
            document.addEventListener("mousedown", handler);
            return () => {
                document.removeEventListener("mousedown", handler);
            }
        }
    });

    return (
        <div className={'header'}>
            <div className={'top_header'}>
                <NavLink to={'/'} className={'logo'}>
                    <img
                        src={'https://w7.pngwing.com/pngs/654/21/png-transparent-alphabet-letter-character-3d-font-text-capital-typography.png'}
                        alt={'logo'}/>
                </NavLink>
                <SearchBar onChange={props.onChange}/>
                {user?
                    <div className={'menu_container'} ref={menuRef}>
                        <div className={'dropdown_trigger'} onClick={openMenu}>
                            <Avatar
                                className={'iconblock__avatar'}
                                src={'/'}
                                sx={{
                                    width: 36,
                                    height: 36,
                                }}
                            />
                        </div>
                        <div className={`dropdown_menu ${open ? 'active' : 'inactive'}`}>
                            <h3 className={'menu_username'}>
                                {username}
                                <span className={'menu_location'}>Москва</span></h3>
                            <ul>
                                <NavLink to={'/cabinet'}>
                                    <DropdownItem text={'профиль'}/>
                                </NavLink>
                                <DropdownItem text={'корзина'}/>
                                <DropdownItem text={'мои фильмы'}/>
                                <DropdownItem text={'избранное'}/>
                                <button className={'logout_btn'} onClick={() => logout()}>
                                    <DropdownItem text={'выход'}/>
                                </button>
                            </ul>
                        </div>
                    </div>
                    :
                    <div className={'login_btn'}>
                        <button onClick={() =>loginRedirector()}>Войти</button>
                    </div>
                }

                <div className={'cart_box'}>
                    <NavLink to={'/cart'} className={'cart_box__button'}>
                        <ShoppingCartIcon fontSize={'small'}/>
                        <span>2250 руб.</span>
                    </NavLink>

                </div>
            </div>

        </div>
    )
}

export default Header;