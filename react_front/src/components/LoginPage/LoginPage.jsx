import React, {Component} from "react";
import Grid from "@mui/material/Grid";
import "./LoginPage.css";
import SignIn from "./SignIn/SignIn";
import SignUp from "./SignUp/SignUp";
import {NavLink, Route, Routes} from "react-router-dom";
import ModalWindow from "../../widgets/ModalWindow/ModalWindow";
import MailPage from "./MailPage/MailPage";
import CodeVerificationPage from "./CodeVerificationPage/CodeVerificationPage";
import ChangePasswordPage from "./ChangePasswordPage/ChangePasswordPage";

class LoginPage extends Component {
  constructor(props) {
    super(props);

    this.state = {
      isLogin: true,
      command: '',
      active: false
    };
  }
  changeLoginState = () => {
    if (this.state.isLogin) this.setState({ isLogin: false });
    else this.setState({ isLogin: true });
  };

  render() {


    let switchScene = (command) => {
      switch (command) {
        case 'mail':
          return <MailPage setCommand={this.setState()}
          />
        case 'code':
          return <CodeVerificationPage setCommand={this.setState()}
          />

        default:
          return <ChangePasswordPage setCommand={this.setState()}
          />
      }
    }


    return (
      <Grid container>
        <Grid item xs={3}></Grid>
        <Grid item xs={6}>
          <div className="login-page__main">
            <div>
              {/*<img src={inst_image} width="454px" alt="" />*/}
            </div>
            <div>
              <div className="login-page__right-component">
                <span className="login-page__logo">КИНОПРОКАТ</span>
                <div className="login-page__sign-in">
                  <Routes>
                    <Route index path={'/login'} element={<SignIn/>}/>
                    <Route path={'/register'} element={<SignUp/>}/>
                  </Routes>

                  <div className="login-page__ordiv">
                    <div className="login-page__divider"></div>
                    <div className="login-page__divider-text">ИЛИ</div>
                    <div className="login-page__divider"></div>
                  </div>
                  <div className="login-page__google">
                    {/*<button className="google__google-btn" type="button">*/}
                    {/*  <img src={go} alt="" /> Продолжить с Google*/}
                    {/*</button>*/}
                  </div>
                  <div className="login-page__forgot-password">
                    <button>Забыл пароль?</button>
                  </div>
                </div>
              </div>

              <div className="login-page__signup-option">
                {this.state.isLogin ? (
                  <div className="login-page__sign-in-prop">
                    <span>
                      Ты ещё не с нами?{" "}
                      <NavLink to={'register'} onClick={() => this.changeLoginState()}>
                        Регистрируйся!
                      </NavLink>

                    </span>
                  </div>
                ) : (
                  <div className="login-page__sign-up-prop">
                    Есть регистрация?{" "}
                    <NavLink to={'login'} onClick={() => this.changeLoginState()}>
                      Входи!
                    </NavLink>

                  </div>
                )}
              </div>
            </div>
          </div>
        </Grid>
        <Grid item xs={3}></Grid>
        <ModalWindow active={modalActive}
                     setActive={setModalActive}
        >
          {switchScene(command)}
        </ModalWindow>
      </Grid>
    );
  }
}

export default LoginPage;
