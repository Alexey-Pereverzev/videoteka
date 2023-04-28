import ReactDOM from "react-dom/client";
import React from "react";
import {DevSupport} from "@react-buddy/ide-toolbox";
import {ComponentPreviews, useInitial} from "../dev";
import App from "../App";
import {
    addMessage,
    addFilm,
    updateMessageHandler,
    login,
    logout,
    register,
    getCurrentUser,
    getAuthHeader, getPublicContent, getUserContent, getManagerContent, getAdminContent, getAllFilms
} from "./state";

import '../index.css';
import reportWebVitals from "../reportWebVitals";

const root = ReactDOM.createRoot(document.getElementById('root'));
export let rerenderEntireTree = (state) =>{
    root.render(
        <React.StrictMode>
            <DevSupport ComponentPreviews={ComponentPreviews}
                        useInitialHook={useInitial}>

                <App state={state}
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
                     // getAllFilms={getAllFilms}
                     updateMessageHandler={updateMessageHandler}/>

            </DevSupport>
        </React.StrictMode>
    );
}
reportWebVitals();