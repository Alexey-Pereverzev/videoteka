import "./StringCard.css"
import DeleteIcon from '@mui/icons-material/Delete';
import axios from "axios";

function StringCard(props) {

    let removeFromCart = async () => {
        console.log(props.filmId)
        try {
            const response = await axios.get('http://localhost:5555/cart/api/v1/cart/remove/',
                {
                    params: {
                        uuid: localStorage.getItem('guestCartId'),
                        filmId: props.filmId,
                    }
                }
            )
            console.log("Ответ метода removeFromCart: " + response)
            props.loadCart()
        } catch (e) {
            alert(e)
        }
    }
    return(
        <div className={'string_container'}>
            <div className={'string__cover'}>
                <img src={props.cover} alt={props.title}/>
            </div>
            <div className={'string__details'}>
                <div className={'string__title'}>{props.title}</div>
            </div>
            <div className={'string_price'}>
                <span>цена: {props.price} руб.</span>
            </div>
            <div className={'string__delete'}>
                <button onClick={() => removeFromCart()}><DeleteIcon/></button>
            </div>


        </div>
    )
}

export default StringCard;