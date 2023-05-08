import React, { Component } from "react";
import "./SignUp.css";


// register(): POST{username, email, password}
class SignUp extends Component {
  constructor(props) {
    super(props);

    this.state = {};
  }

  render() {
      function sendRegisterRequest() {

      }

      return (
      <div>
        <input
          className="signup-page__sign-in__text"
          id="username"
          type="text"
          placeholder="Псевдоним"
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
