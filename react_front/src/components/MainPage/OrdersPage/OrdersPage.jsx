import "./OrdersPage.css";
import axios from "axios";
import RentCard from "../../../widgets/RentCard/RentCard";
import OwnCard from "../../../widgets/OwnCard/OwnCard";
import {Component} from "react";
import {MenuItem, Paper, Select, Table, TableBody, TableCell, TableContainer, TableHead, TableRow} from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";

class OrdersPage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            films: [],
            saleFilms: [],
            rentFilms: [],
            isSale: false

        }
    }

    componentDidMount() {
        this.getRentedOrders()
        this.getSoldOrders()
    }


    getRentedOrders = () => {
        try {
            axios.get('http://localhost:5555/cabinet/api/v1/orders/rent')
                .then(response => response.data)
                .then(data => {
                    console.log(data)
                    this.setState({
                        rentFilms: data,
                    })
                }).catch(reason => console.log(reason))
        } catch (e) {
            console.log(e)
        }

    }
    getSoldOrders = () => {
        try {
            axios.get('http://localhost:5555/cabinet/api/v1/orders/sale')
                .then(response => response.data)
                .then(data => {
                    console.log(data)
                    this.setState({
                        saleFilms: data,
                    })
                })
        } catch (e) {
            console.log(e)
        }

    }


    render() {
        function buyRentedFilm(id) {
            
        }

        return (
            <div className={'orders_container'}>
                <div className={'rent_box__container'}>
                    <span>Арендованные фильмы</span>


                        <div className={'rent_box'}>
                           
                                <TableContainer component={Paper}>
                                    <Table stickyHeader={true} sx={{minWidth: 1050}} aria-label="sticky table">
                                        <TableHead>
                                            <TableRow>
                                                <TableCell sx={{background: '#2b303b', color: 'white'}}>Обложка</TableCell>
                                                <TableCell sx={{background: '#2b303b', color: 'white'}} align="right">Название</TableCell>
                                                <TableCell sx={{background: '#2b303b', color: 'white'}} align="right">Цена</TableCell>
                                                <TableCell sx={{background: '#2b303b', color: 'white'}} align="right">Старт аренды</TableCell>
                                                <TableCell sx={{background: '#2b303b', color: 'white'}} align="right">Финиш аренды</TableCell>

                                            </TableRow>
                                        </TableHead>
                                        <TableBody>
                                            {this.state.rentFilms.map((row) => (           
                                                <TableRow
                                                    hover role="checkbox"
                                                    tabIndex={-1}
                                                    key={row.username}
                                                    sx={{'&:last-child td, &:last-child th': {border: 0}}}
                                                >
                                                    <TableCell component="th" scope="row">
                                                        <img className={'orders_img'} src={row.imageUrlLink}/>
                                                    </TableCell>
                                                    <TableCell align="right">{row.filmTitle}</TableCell>
                                                    <TableCell align="right">{row.price}</TableCell>
                                                    <TableCell align="right">{row.rentStart}</TableCell>
                                                    <TableCell align="right">{row.rentEnd}</TableCell>
                                                    <TableCell align="right"><button className={'delete-user__btn'} onClick={() => buyRentedFilm(row.id)}>Купить</button></TableCell>
                                                </TableRow>
                                            ))}
                                        </TableBody>
                                    </Table>
                                </TableContainer>
                        </div>

                    </div>




                    <div className={'rent_box__container'}>
                        <span>Ваши фильмы</span>

                        <div className={'own_box'}>
                            <TableContainer component={Paper}>
                                <Table stickyHeader={true} sx={{minWidth: 1050}} aria-label="sticky table">
                                    <TableHead>
                                        <TableRow>
                                            <TableCell sx={{background: '#2b303b', color: 'white'}}>Обложка</TableCell>
                                            <TableCell sx={{background: '#2b303b', color: 'white'}} align="right">Название</TableCell>
                                            <TableCell sx={{background: '#2b303b', color: 'white'}} align="right">Цена</TableCell>
                                        </TableRow>
                                    </TableHead>
                                    <TableBody>
                                        {this.state.saleFilms.map((row) => (
                                            <TableRow
                                                hover role="checkbox"
                                                tabIndex={-1}
                                                key={row.username}
                                                sx={{'&:last-child td, &:last-child th': {border: 0}}}
                                            >
                                                <TableCell component="th" scope="row">
                                                    <img className={'orders_img'} src={row.imageUrlLink}/>
                                                </TableCell>
                                                <TableCell align="right">{row.filmTitle}</TableCell>
                                                <TableCell align="right">{row.price}</TableCell>
                                            </TableRow>
                                        ))}
                                    </TableBody>
                                </Table>
                            </TableContainer>
                        </div>

                    </div>

            </div>


        )
    }
}

export default OrdersPage;