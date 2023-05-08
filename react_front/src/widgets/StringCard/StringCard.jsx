import "./StringCard.css"
import DeleteIcon from '@mui/icons-material/Delete';

function StringCard(props) {
    return(
        <div className={'string_container'}>
            <div className={'string__cover'}>
                <img src={props.cover} alt={props.title}/>
            </div>
            <div className={'string__details'}>
                <div className={'string__title'}>{props.title}</div>
                <div className={'string__description'}>{props.description}</div>
                <div className={'string__detail'}>
                    {props.director.map((director) => <span>{director}</span>)}
                    <span>{props.startPremierYear}</span>
                    {props.genre.map((genre) => <span>{genre}</span>)}
                </div>
            </div>
            <div className={'string__quantity'}>
                <span>{props.quantity}</span>
            </div>
            <div className={'string_price'}>
                <span>{props.price}</span>
            </div>
            <div className={'string__delete'}>
                <button><DeleteIcon/></button>
            </div>


        </div>
    )
}

export default StringCard;