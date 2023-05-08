import './App.css';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import LoginPage from "./components/LoginPage/LoginPage";
import MainPage from "./components/MainPage/MainPage";
import {
    clearState,
    getAllCountries,
    getAllDirectors,
    getAllFilms,
    getAllGenres,
    getMinMaxPrice
} from "./adapters/state";


function App(props) {
  return (
      <div className="App">
        <BrowserRouter>
          <Routes>
            <Route path='/login' element={<LoginPage
                login={props.login}
                register={props.register}

            />}
            />
            <Route path={'/*'} element={<MainPage
                logout={props.logout}
                getCurrentUser={props.getCurrentUser}
                addFilm={props.addFilm}
                getAllFilms={props.getAllFilms}
                getMinMaxPrice={props.getMinMaxPrice}
                getAllGenres={props.getAllGenres}
                getAllDirectors={props.getAllDirectors}
                getAllCountries={props.getAllCountries}
                clearState={props.clearState}
            />}/>
          </Routes>
        </BrowserRouter>
      </div>
  );
}


export default App;
