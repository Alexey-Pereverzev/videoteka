import React,
{
    Component
}

    from "react";
import "./SignIn.css";


//all changes rolledback - Aleksei

// login(): POST{email, password} & save JWT to Local Storage
class SignIn extends Component {
    constructor(props) {
        super(props);

        this.state = {}

        ;
    }

    render() {
        function sendLoginRequest() {

        }

        return (
            <div>
                <input className="login-page__sign-in__text"
                       id="username"
                       type="text"
                       placeholder="Почта, имя пользователя"
                /> <input className="login-page__sign-in__text"
                          id="password"
                          type="password"
                          placeholder="Вводи пароль"
            />
                <button id="submit" className="login-page__sign-in__button" onClick={() => sendLoginRequest()}>Вхожу
                </button>
            </div>);
    }
}

export default SignIn;