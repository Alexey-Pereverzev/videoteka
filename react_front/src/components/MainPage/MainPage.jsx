import style from "./MainPage.module.css"
import Header from "../UI/Header/Header";
import FilmCard from "../../widgets/FilmCard/FilmCard";
import {Component} from "react";
import axios from "axios";
import {Button, ButtonGroup, FormControl, InputLabel, MenuItem, Pagination, Select} from "@mui/material";
import Footer from "../UI/Footer/Footer";
import {Route, Routes} from "react-router-dom";
import CatalogPage from "./CatalogPage/CatalogPage";
import CartPage from "./CartPage/CartPage";


class MainPage extends Component {
    constructor(props) {
        super(props);

        this.state = {
            currentPage: 1,
            active: false,
            modal: false
        }
    }


    getFilmByTitlePart = (currentPage, value) => {
        const titlePart = value.nativeEvent.data
        currentPage -= 1;
        console.log(titlePart)
        axios.get("http://localhost:5555/catalog/api/v1/film/find_by_title_part",
            {
                params: {
                    currentPage,
                    titlePart
                }
            })
            .then(response => response.data)
            .then((data) => {
                if (data !== null) {
                    console.log(data.content)
                    this.setState({
                        films: data.content,
                        totalPages: data.totalPages,
                        totalElements: data.totalElements,
                        currentPage: data.number + 1
                    })
                } else {
                    if (data === null) {
                        return (
                            <div>
                                <h4>Ничего нет</h4>
                            </div>
                        )
                    }
                }

            }).catch((error) => {
            console.error("Error: " + error)
        })
    }

    render() {

        return (
            <div className={style.container}>
                <Header logout={this.props.logout}
                        onChange={(value) => this.getFilmByTitlePart(this.state.currentPage, value)}
                />
                <div className={style.main_container}>
                    <Routes>
                        <Route index path={'/'} element={<CatalogPage/>}/>
                        <Route path={'cart'} element={<CartPage/>}/>
                        <Route/>
                    </Routes>
                </div>
                <div>
                    <Footer/>
                </div>
            </div>
        )
    }
}

export default MainPage;