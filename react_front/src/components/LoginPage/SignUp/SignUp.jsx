import React, {Component} from "react";
import "./SignUp.css";
import axios from "axios";
import {toast, ToastContainer} from "react-toastify";

//     username,
//     password,
//     confirmPassword,
//     email,
//     firstName,
//     lastName,
//     phoneNumber,
//     address


//http://localhost:5555/auth/api/v1/reg/register
// register(): POST{username, email, password, firstName, lastName, phoneNumber,address}
class SignUp extends Component {
    constructor(props) {
        super(props);
        this.state = {
            isLoggedIn: false
        };

    }

     displayCartNotification = (message) => {
        toast.success(message);
    }
    sendRegisterRequest = (event) => {
        event.preventDefault(true);
        return axios.post('http://localhost:5555/auth/api/v1/reg/register',
            {
                username: event.target.username.value,
                password: event.target.password.value,
                confirmPassword: event.target.confirmPassword.value,
                email: event.target.email.value,
                firstName: event.target.firstName.value,
                lastName: event.target.lastName.value,
                phoneNumber: event.target.phoneNumber.value,
                address: event.target.address.value
            })
            .then(response => {

                console.log(response.data)
                alert("Вы успешно зарегистрированы")
                window.location = "/"
            },
                function errorCallback(response) {
                console.log(response)
                    let displayCartNotification = (message) => {
                        toast.error(message);
                    }
                    displayCartNotification(response.response.data.value)
            }
            )

    }

    render() {


        return (
            <div>
                <form onSubmit={(event) => this.sendRegisterRequest(event)}>
                    <input
                        className="signup-page__sign-in__text"
                        name={'username'}
                        id="username"
                        type="text"
                        placeholder="Псевдоним"
                    />
                    <input
                        className="signup-page__sign-in__text"
                        name={'password'}
                        id="password"
                        type="password"
                        placeholder="Пароль"
                    />
                    <input
                        className="signup-page__sign-in__text"
                        name={'confirmPassword'}
                        id="another_password"
                        type="password"
                        placeholder="Пароль ещё раз"
                    />
                    <input
                        className="signup-page__sign-in__text"
                        name={'email'}
                        id="email"
                        type="email"
                        placeholder="почта"
                    />
                    <input
                        className="signup-page__sign-in__text"
                        name={'firstName'}
                        id="firstName"
                        type="text"
                        placeholder="Имя"
                    />
                    <input
                        className="signup-page__sign-in__text"
                        name={'lastName'}
                        id="lastName"
                        type="text"
                        placeholder="Фамилия"
                    />
                    <input
                        className="signup-page__sign-in__text"
                        name={'phoneNumber'}
                        id="phoneNumber"
                        type="text"
                        placeholder="Номер телефона"
                    />
                    <input
                        className="signup-page__sign-in__text"
                        name={'address'}
                        id="addres"
                        type="text"
                        placeholder="Адрес"
                    />
                    <button id="submit" className="login-page__sign-in__button">Регистрируюсь</button>
                </form>
                <ToastContainer
                    position="top-right"
                    autoClose={5000}
                    hideProgressBar={false}
                    newestOnTop={false}
                    closeOnClick
                    rtl={false}
                    pauseOnFocusLoss
                    draggable
                    pauseOnHover
                    theme="dark"
                />
            </div>
        );
    }
}

export default SignUp;
