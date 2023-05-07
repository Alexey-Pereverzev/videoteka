import style from './FilmCard.module.css'
import {Rating} from "@mui/material";
import TagButton from "../TagButton/TagButton";
import {useState} from "react";
import ModalWindow from "../ModalWindow/ModalWindow";
import FilmPage from "../../components/MainPage/FilmPage/FilmPage";


function FilmCard(props) {
    const [modalActive, setModalActive] = useState(false);
    return(
        <div className={style.container}>

            <div className={style.card} onClick={() => setModalActive(true)}>
                <div className={style.poster}>
                    <img src={props.imageUrlLink} alt="" />
                </div>

                <div className={style.details}>
                    <h2>
                        {props.title}<br/>
                        {props.director.map((director) => <span>Режисёр: {director}</span>)}

                    </h2>
                    <div className={style.rating}>
                        <Rating name="read-only" value={4} readOnly />
                        <span>4/5</span>
                    </div>
                    {props.genre.map((genre) => <TagButton genre={genre}/>)}

                    <div className={style.info}>
                        <p>{props.description}</p>
                    </div>
                    <div className={style.country}>
                        <h4>{props.country}</h4>
                    </div>
                </div>
                <div className="card-footer">
                    <div className="clearfix">
                    </div>
                </div>
            </div>
            <ModalWindow active={modalActive}
                         setActive={setModalActive}
            >
                <FilmPage director={props.director}
                          isSale={props.isSale}
                          title={props.title}
                          premierYear={props.premierYear}
                          country={props.country}
                          description={props.description}
                          genre={props.genre}
                          rentPrice={props.rentPrice}
                          salePrice={props.salePrice}
                          cover={props.imageUrlLink}
                />
            </ModalWindow>
        </div>
    )
}
export default FilmCard;