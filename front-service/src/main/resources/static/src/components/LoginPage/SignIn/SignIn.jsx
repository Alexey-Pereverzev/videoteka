import React, {Component} from "react";
import "./SignIn.css";
// import "react-toastify/dist/ReactToastify.css";
import axios, {post} from "axios";
// import {toast, ToastContainer} from "react-toastify";
import jwt from 'jwt-decode'
import {Alert} from "@mui/material";


// login(): POST{email, password} & save JWT to Local Storage
class SignIn extends Component {

    constructor(props) {
        super(props);

        this.state = {
            isLoggedIn: false
        }
    }

    sendLoginRequest = (event) => {
        event.preventDefault(true);
        const username = event.target.username.value
        try {
            return axios
                .post('http://localhost:5555/auth/api/v1/auth/authenticate', {
                    username: event.target.username.value,
                    password: event.target.password.value,
                }).then(response => {
                    console.log(response.data)
                    if (response.data.token) {
                        axios.defaults.headers.common.Authorization = 'Bearer ' + response.data.token
                        localStorage.setItem("customer", JSON.stringify(response.data))
                        localStorage.setItem("username", JSON.stringify(username))
                        this.showCurrentUserInfo()

                        console.log(localStorage.getItem("customer"))


                        axios.get('http://localhost:5555/cart/api/v1/cart/' + localStorage.getItem('guestCartId') + '/merge')
                            .then(response => response.data)

                    }
                    window.location = "/"
                    return response.data

                })
                .then(data => {return<Alert severity="error">Привет, {data.username}!</Alert>
        })
        }catch (e) {
             return <Alert severity="error">{e}</Alert>

        }

    }
    showCurrentUserInfo = () =>{
        if(this.isUserLoggedIn){
            let customer = JSON.parse(localStorage.getItem('customer'))
            let token = customer.token
            let payload = jwt(token)
            let userId = payload.sub
            localStorage.setItem("userId", JSON.stringify(userId))
        } else {
            alert('UNAUTHORIZED')
        }
    }
    isUserLoggedIn = () =>{
        return !!localStorage.getItem('customer');
    }

    render() {
        function authHeaderHandler() {
            const customer = JSON.parse(localStorage.getItem('customer'))
            if (customer && customer.token) {

                return {Authorization: 'Bearer' + customer.token}
            } else {
                toast.warn('🦄 Нет такого пользователя!', {
                    position: "bottom-left",
                    autoClose: 5000,
                    hideProgressBar: false,
                    closeOnClick: true,
                    pauseOnHover: true,
                    draggable: true,
                    progress: undefined,
                    theme: "colored",
                });
                return {}
            }
        }

        return (
            <div>
                <form method={'post'} onSubmit={(event) => this.sendLoginRequest(event)}>
                    <input className="login-page__sign-in__text"
                           name={'username'}
                           id="username"
                           type="text"
                           placeholder="Почта, имя пользователя"
                    /> <input className="login-page__sign-in__text"
                              name={'password'}
                              id="password"
                              type="password"
                              placeholder="Вводи пароль"
                />
                    <button id="submit"
                            className="login-page__sign-in__button"
                    >
                        Вхожу
                    </button>
                </form>

            </div>
        );
    }
}

export default SignIn;