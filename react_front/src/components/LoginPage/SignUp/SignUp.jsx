import React, {Component} from "react";
import "./SignUp.css";
import axios from "axios";


// register(): POST{username, email, password, firstName, lastName, phoneNumber,address}
class SignUp extends Component {
    constructor(props) {
        super(props);

        this.state = {};
    }

    render() {
        function sendRegisterRequest(username,
                                     password,
                                     confirmPassword,
                                     email,
                                     firstName,
                                     lastName,
                                     phoneNumber,
                                     address
        ) {
            return axios
                .post("http://localhost:5555/auth/api/v1/reg/register", {
                    username,
                    password,
                    confirmPassword,
                    email,
                    firstName,
                    lastName,
                    phoneNumber,
                    address
                })
                .then(response => response.data)
        }

        return (
            <div>
                <form>
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


                    <button id="submit" className="login-page__sign-in__button"
                            onClick={() => sendRegisterRequest()}>Регистрируюсь
                    </button>
                </form>

            </div>
        );
    }
}

export default SignUp;
