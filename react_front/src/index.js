
import './index.css';
import reportWebVitals from './reportWebVitals';
import state, {
    addFilm,
    getAdminContent,
    getAuthHeader,
    getCurrentUser, getManagerContent,
    getPublicContent,
    getUserContent,
    login,
    logout,
    register, updateMessageHandler
} from "./adapters/state";
import {rerenderEntireTree} from "./adapters/render";
import * as ReactDom from "react-dom";
import App from "./App";
import React, {useEffect} from "react";
import axios from "axios";


// rerenderEntireTree(state)
// reportWebVitals();
function run() {
    console.log("Вошли в метод run()")
    if (localStorage.getItem('customer')){
        let user = localStorage.getItem('customer')
        let token = JSON.parse(user)

        try {
            let jwt = token.token
            let payload = JSON.parse(atob(jwt.split('.')[1]))
            let currentTime = parseInt(new Date().getTime() / 1000)

            if(currentTime > payload.exp
                // && window.location.href !== "http://localhost:3000/gate/login"
            ){
                alert("Токен простыл")
                localStorage.clear('customer')
                token.token = null
                axios.defaults.headers.common.Authorization = ''
                console.log( axios.defaults.headers.common)
                console.log( localStorage.getItem('customer'))
                // window.location = "/gate/login"
            }

        }catch (e) {
            console.log("Ошибка: " + e)
        }
        if (localStorage.getItem('customer')){
            console.log(token.token)
            axios.defaults.headers.common.Authorization = 'Bearer ' + token.token
        }

    }
    if (!localStorage.getItem("guestCartId")){
        console.log('Вошли в метод генерации корзины')
        axios.get("http://localhost:5555/cart/api/v1/cart/generate",{
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            }
        })
            .then(response =>{
                console.log('Генерация корзины. Голый респонс: '+response)
                localStorage.setItem('guestCartId',  JSON.stringify(response.data.value))
                console.log('Информация из хранилища. Корзина: '+localStorage.getItem('guestCartId'))
            })
    }
}
useEffect(() => {
    run()
}, []);
ReactDom.render(
    <App
         login={login}
         logout={logout}
         register={register}
         getCurrentUser={getCurrentUser}
         getAuthHeader={getAuthHeader}
         getPublicContent={getPublicContent}
         getUserContent={getUserContent}
         getManagerContent={getManagerContent}
         getAdminContent={getAdminContent}
         addFilm={addFilm}
         updateMessageHandler={updateMessageHandler}/>,
    document.getElementById('root')
)
