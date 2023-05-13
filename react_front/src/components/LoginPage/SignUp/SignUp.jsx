import React, { Component } from "react";
import "./SignUp.css";
import axios from "axios";


// register(): POST{username, email, password, firstName, lastName, phoneNumber,address}
class SignUp extends Component {
  constructor(props) {
    super(props);

    this.state = {};
  }

  render() {
      function sendRegisterRequest(username, email, password) {
          return axios
              .post("http://localhost:5555/catalog/api/v1/reg/register", {
                  username,
                  email,
                  password
              })
      }

      return (
      <div>
        <input
          className="signup-page__sign-in__text"
          id="username"
          type="text"
          placeholder="Псевдоним"
        /><input
          className="signup-page__sign-in__text"
          id="firstName"
          type="text"
          placeholder="Имя"
        /><input
          className="signup-page__sign-in__text"
          id="lastName"
          type="text"
          placeholder="Фамилия"
        /><input
          className="signup-page__sign-in__text"
          id="phoneNumber"
          type="text"
          placeholder="Номер телефона"
        /><input
          className="signup-page__sign-in__text"
          id="addres"
          type="text"
          placeholder="Адрес"
        />
        <input
          className="signup-page__sign-in__text"
          id="email"
          type="email"
          placeholder="почта"
        />
        <input
          className="signup-page__sign-in__text"
          id="password"
          type="password"
          placeholder="Пароль"
        />
        <input
          className="signup-page__sign-in__text"
          id="another_password"
          type="password"
          placeholder="Пароль ещё раз"
        />
        <button id="submit" className="login-page__sign-in__button" onClick={() => sendRegisterRequest()}>Регистрируюсь</button>
      </div>
    );
  }
}

export default SignUp;
