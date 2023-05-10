import './App.css';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import MainPage from "./components/MainPage/MainPage";
import LoginPage from "./components/LoginPage/LoginPage";
import axios from "axios";


function App(props) {
    function run() {
        if (localStorage.getItem('customer')){
            let user = localStorage.getItem('customer')
            let token = JSON.parse(user)

            try {
                let jwt = token.token
                let payload = JSON.parse(atob(jwt.split('.')[1]))
                let currentTime = parseInt(new Date().getTime() / 1000)
                if(currentTime > payload.exp){
                    console.log("Токен простыл!")
                    delete localStorage.getItem('customer')
                    axios.defaults.headers.common.Authorization = ''
                }
            }catch (e) {

            }
            if (localStorage.getItem('customer')){
                console.log(token.token)
                axios.defaults.headers.common.Authorization = 'Bearer ' + token.token
            }
        }
        if (localStorage.getItem("cartId")){
            axios.get("http://localhost:5555/cart/api/v1/cart/generate")
                .then(response =>{
                    localStorage.setItem('cartId',  JSON.stringify(response.data.value))
            })
        }
    }
    run()
  return (
      <div className="App">
        <BrowserRouter>
          <Routes>
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
              <Route path='/login' element={<LoginPage
                  login={props.login}
                  register={props.register}

              />}
              />
          </Routes>
        </BrowserRouter>
      </div>
  );
}


export default App;
