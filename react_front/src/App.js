import './App.css';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import LoginPage from "./components/LoginPage/LoginPage";
import MainPage from "./components/MainPage/MainPage";


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
            />}/>
          </Routes>
        </BrowserRouter>
      </div>
  );
}


export default App;
