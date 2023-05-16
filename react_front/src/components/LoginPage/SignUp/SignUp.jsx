import React, {Component} from "react";
import "./SignUp.css";
import axios from "axios";

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
        this.state = {};

    }

    sendRegisterRequest = (event) => {

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
            }
        )
            .then(response => {
                if (response.data.token) {
                    axios.defaults.headers.common.Authorization = 'Bearer ' + response.data.token
                    localStorage.setItem("customer", JSON.stringify(response.data))
                    localStorage.setItem("username", JSON.stringify(response.data.username))
                    this.showCurrentUserInfo()
                    console.log(localStorage.getItem("customer"))

                    axios.get('http://localhost:5555/cart/api/v1/cart/' + localStorage.getItem('guestCartId') + '/merge')
                        .then(response => {
                        })
                    window.location = "/"
                }
                return response.data
            })
    }

    render() {


        return (
            <div>
                <form method={'post'} onSubmit={(event) => this.sendRegisterRequest(event)}>
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
            </div>
        );
    }
}

export default SignUp;
