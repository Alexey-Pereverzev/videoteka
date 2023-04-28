import style from './FilmCard.module.css'
import {Icon, Rating} from "@mui/material";
import TagButton from "../TagButton/TagButton";

// title,
//     imageUrlLink,
//     premierYear,
//     country,
//     genre,
//     director,
//     description

function FilmCard(props) {
    return(
        <div className={style.container}>
            <div className={style.card}>
                <div className={style.poster}>
                    <img src={props.imageUrlLink} alt="" />
                </div>

                <div className={style.details}>
                    <h2>
                        {props.title}<br/>
                        <span>Режисёр: {props.director}</span>
                    </h2>
                    <div className={style.rating}>
                        <Rating name="read-only" value={4} readOnly />
                        <span>4/5</span>
                    </div>
                    <TagButton genre={props.genre}/>
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
        </div>
    )
}
export default FilmCard;