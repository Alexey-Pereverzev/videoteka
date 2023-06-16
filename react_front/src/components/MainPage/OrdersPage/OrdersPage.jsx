import "./OrdersPage.css";
import axios from "axios";
import {Component} from "react";
import {Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow} from "@mui/material";
import ModalWindow from "../../../widgets/ModalWindow/ModalWindow";


class OrdersPage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            films: [],
            saleFilms: [],
            rentFilms: [],
            isSale: false,
            youtubeID: 'Zzrh35vCfeI',
            modalActive: false

        }
    }

    componentDidMount() {
        this.getRentedOrders()
        this.getSoldOrders()
    }

    changeHandler = () => {
        if (this.state.modalActive === true) {
            this.setState({modalActive: false})
        } else {
            this.setState({modalActive: true})
        }

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
        async function buyRentedFilm(id, title, cover, price) {
            try {
                const response = await axios.get('http://localhost:5555/cart/api/v1/cart/add',
                    {
                        params: {
                            uuid: localStorage.getItem('guestCartId'),
                            filmId: id,
                            filmTitle: title,
                            filmPrice: price,
                            filmImageUrlLink: cover,
                            isSale: true
                        }
                    }
                )
                console.log("Ответ метода buyRentedFilm: " + response.data)
                window.location = '/cart'
            } catch (e) {
                alert(e)
            }
        }

        let showVideo = () => {
            return <iframe
                src="https://vk.com/video_ext.php?oid=-210183487&id=456241708&hash=f71b473e86dbfd54" width="640"
                height="360" frameBorder="0" allowFullScreen="1"
                allow="autoplay; encrypted-media; fullscreen; picture-in-picture">

            </iframe>
            // <iframe className='video'
            //          title='Youtube player'
            //          sandbox='allow-same-origin allow-forms allow-popups allow-scripts allow-presentation'
            //          src={`https://youtube.com/embed/${this.state.youtubeID}?autoplay=0`}>
            //  </iframe>

        }

        return (
            <div className={'orders_container'}>
                <div className={'rent_box__container'}>
                    <span>Арендованные фильмы</span>


                    <div className={'rent_box'}>

                        <TableContainer component={Paper}>
                            <Table stickyHeader={true} sx={{minWidth: 850}} aria-label="sticky table">
                                <TableHead>
                                    <TableRow>
                                        <TableCell sx={{background: '#2b303b', color: 'white'}}>Обложка</TableCell>
                                        <TableCell sx={{background: '#2b303b', color: 'white'}}
                                                   align="right"></TableCell>
                                        <TableCell sx={{background: '#2b303b', color: 'white'}}
                                                   align="right"></TableCell>
                                        <TableCell sx={{background: '#2b303b', color: 'white'}}
                                                   align="right">Название</TableCell>
                                        <TableCell sx={{background: '#2b303b', color: 'white'}}
                                                   align="right">Цена</TableCell>
                                        <TableCell sx={{background: '#2b303b', color: 'white'}} align="right">Старт
                                            аренды</TableCell>
                                        <TableCell sx={{background: '#2b303b', color: 'white'}} align="right">Финиш
                                            аренды</TableCell>
                                        <TableCell sx={{background: '#2b303b', color: 'white'}}
                                                   align="right"></TableCell>

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
                                                <img className={'orders_img'} src={row.imageUrlLink} alt={'обложка'}/>
                                            </TableCell>
                                            <TableCell align="right">
                                                <button className={'buy-order__btn'}
                                                        onClick={() => this.setState({modalActive: true})}>Смотреть
                                                </button>
                                            </TableCell>
                                            <TableCell align="right">
                                                <button className={'buy-order__btn'}
                                                        onClick={() => buyRentedFilm(row.id, row.filmTitle, row.imageUrlLink, row.price)}>Купить
                                                </button>
                                            </TableCell>
                                            <TableCell align="right">{row.filmTitle}</TableCell>
                                            <TableCell align="right">{row.price}</TableCell>

                                            <TableCell
                                                align="right">{new Date(row.rentStart).toLocaleDateString("ru-RU", {
                                                day: "numeric",
                                                month: "long",
                                                year: "numeric"
                                            })}</TableCell>
                                            <TableCell
                                                align="right">{new Date(row.rentEnd).toLocaleDateString("ru-RU", {
                                                day: "numeric",
                                                month: "long",
                                                year: "numeric"
                                            })}</TableCell>
                                            <TableCell align="right">

                                            </TableCell>
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
                            <Table stickyHeader={true} sx={{minWidth: 850}} aria-label="sticky table">
                                <TableHead>
                                    <TableRow>
                                        <TableCell sx={{
                                            background: '#2b303b',
                                            color: 'white',
                                            maxWidth: 50
                                        }}>Обложка</TableCell>
                                        <TableCell sx={{background: '#2b303b', color: 'white', maxWidth: 77}}
                                                   align="center">Название</TableCell>
                                        <TableCell sx={{background: '#2b303b', color: 'white', maxWidth: 77}}
                                                   align="center">Цена</TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {this.state.saleFilms.map((row) => (
                                        <TableRow
                                            hover role="checkbox"
                                            tabIndex={-1}
                                            key={row.username}
                                            sx={{'&:last-child td, &:last-child th': {border: 0}}}
                                            onClick={() => this.setState({modalActive: true})}
                                        >
                                            <TableCell component="th" scope="row">
                                                <img className={'orders_img'} src={row.imageUrlLink}/>
                                            </TableCell>
                                            <TableCell align="center">{row.filmTitle}</TableCell>
                                            <TableCell align="center">{row.price}</TableCell>
                                        </TableRow>
                                    ))}
                                </TableBody>
                            </Table>
                        </TableContainer>
                    </div>

                </div>
                <ModalWindow active={this.state.modalActive}
                             setActive={this.changeHandler}
                >
                    {showVideo()}
                </ModalWindow>
            </div>


        )
    }
}

export default OrdersPage;