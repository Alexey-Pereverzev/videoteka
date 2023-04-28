import style from "./MainPage.module.css"
import Header from "../UI/Header/Header";
import FilmCard from "../../widgets/FilmCard/FilmCard";
import {Component} from "react";
import axios from "axios";
import {Icon,} from "@mui/material";
import {Button, FormControl, Pagination} from "react-bootstrap";
import {ArrowBackIos} from "@mui/icons-material";

class MainPage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            films: [],
            filmsPerPage: 4,
            currentPage: 1
        }
    }

    componentDidMount() {
        this.getAllFilms(this.state.currentPage)
    }
getAllFilms = (currentPage) => {
        currentPage -= 1;
        axios.get("http://localhost:5555/catalog/api/v1/film/list_all?currentPage=" + currentPage + "&size=" + this.state.filmsPerPage)
            .then(response => response.data)
            .then((data) => {
                this.setState({
                    films: data.content,
                    totalPages: data.totalPages,
                    totalElements: data.totalElements,
                    currentPage: data.number + 1
                })
            }).catch((error) => {
            console.error("Error: " + error)
        })
    }

    render() {
        const {films, currentPage, filmsPerPage} = this.state;
        const lastIndex = currentPage * filmsPerPage;
        const firstIndex = lastIndex - filmsPerPage;
        // const currentFilms = films.slice(firstIndex, lastIndex);
        const totalPages = this.state.totalPages;
    return(
        <div className={style.container}>
            <Header logout={this.props.logout}/>
            <div className={style.catalog}>
                {
                    films.length === 0 ?
                        <div className={style.empty}>
                            <h1>Неть</h1>
                        </div> :
                       films.map((film) => (
                            <FilmCard imageUrlLink={film.imageUrlLink}
                                      id={film.id}
                                      title={film.title}
                                      premierYear={film.premierYear}
                                      country={film.country}
                                      genre={film.genre}
                                      director={film.director}
                                      description={film.description}/>

                        ))

                }


            </div>

            <div className={style.pagination}>
                {
                    films.length > 0 ?
                        <div>
                            <div className={style.current_pages}>
                                <h4>Это {currentPage} страница из {totalPages}</h4>
                            </div>
                            <div className={style.pagination_items}>


                            </div>
                        </div>

                        :
                        <div className={style.empty}>
                            <h1>Неть</h1>
                        </div>
                }
            </div>

        </div>
    )
}


}

export default MainPage;