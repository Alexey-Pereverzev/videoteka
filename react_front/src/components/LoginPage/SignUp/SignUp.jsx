import React, {Component} from "react";
import "./SignUp.css";
import axios from "axios";
import {toast, ToastContainer} from "react-toastify";

const wait = (ms = 4) => new Promise((resolve) => setTimeout(resolve, ms));

export default class SignUp extends Component {
    constructor(props) {
        super(props);
        this.state = {
            isLoggedIn: false
        };
        this.sendRegisterRequest = this.sendRegisterRequest.bind(this);
    }

    async sendRegisterRequest(event) {
        try {
            event.preventDefault(true);
            const response = await axios.post('http://localhost:5555/auth/api/v1/reg/register', {
                username: event.target.username.value,
                password: event.target.password.value,
                confirmPassword: event.target.confirmPassword.value,
                email: event.target.email.value,
                firstName: event.target.firstName.value,
                lastName: event.target.lastName.value,
                phoneNumber: event.target.phoneNumber.value,
                address: event.target.address.value
            });
            console.log("Перед алертом");
            alert("Вы успешно зарегистрированы");
            // await wait(3000);
            console.log("Перед редиректом");
            window.location = "/"
        } catch (err) {
            console.error("Register request error");
            console.error(err);
            toast.error(err.response.data.value);
        }
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

