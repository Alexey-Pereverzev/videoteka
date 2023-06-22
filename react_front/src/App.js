import './App.css';
import {BrowserRouter, Route, Routes} from "react-router-dom";
// import MainPage from "./components/MainPage/MainPage";
// import LoginPage from "./components/LoginPage/LoginPage";
import {lazy, Suspense} from "react";
import {CircularProgress} from "@mui/material";
const MainPage = lazy(() => import('./components/MainPage/MainPage'))
const LoginPage = lazy(() => import('./components/LoginPage/LoginPage'))

function App(props) {
  return (
      <div className="App">
        <BrowserRouter>
          <Routes>
            <Route path={'/*'} element={
                <Suspense fallback={<CircularProgress color="secondary" sx={{mt: 25, ml: 70}}/>}>
                    <MainPage
                    logout={props.logout}
                    getCurrentUser={props.getCurrentUser}
                    addFilm={props.addFilm}
                    getAllFilms={props.getAllFilms}
                    getMinMaxPrice={props.getMinMaxPrice}
                    getAllGenres={props.getAllGenres}
                    getAllDirectors={props.getAllDirectors}
                    getAllCountries={props.getAllCountries}
                    clearState={props.clearState}
                />
                </Suspense>
               }/>
              <Route path='/gate/*' element={
                  <Suspense fallback={<CircularProgress color="secondary" sx={{mt: 25, ml: 70}}/>}>
                      <LoginPage/>
                  </Suspense>
              }/>
          </Routes>
        </BrowserRouter>
      </div>
  );
}


export default App;
