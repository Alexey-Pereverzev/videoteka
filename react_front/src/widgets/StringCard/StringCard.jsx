import "./StringCard.css"
import DeleteIcon from '@mui/icons-material/Delete';
import RemoveCircleOutlineIcon from '@mui/icons-material/RemoveCircleOutline';
import AddCircleOutlineIcon from '@mui/icons-material/AddCircleOutline';

function StringCard(props) {
    return(
        <div className={'string_container'}>
            <div className={'string__cover'}>
                <img src={props.cover} alt={props.title}/>
            </div>
            <div className={'string__details'}>
                <div className={'string__title'}>{props.title}</div>
            </div>
            <div className={'string__quantity'}>
                <RemoveCircleOutlineIcon fontSize={'small'} sx={{backgroundColor: 'red', borderRadius: '50px'}}/>
                <span>{props.quantity}</span>
                <AddCircleOutlineIcon fontSize={'small'} sx={{backgroundColor: 'green', borderRadius: '50px'}}/>
            </div>
            <div className={'string_price'}>
                <span>цена: {props.price} руб.</span>
            </div>
            <div className={'string__delete'}>
                <button><DeleteIcon/></button>
            </div>


        </div>
    )
}

export default StringCard;