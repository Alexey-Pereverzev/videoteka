import "./SearchBar.css"
import SearchIcon from '@mui/icons-material/Search';
import {useState} from "react";

function SearchBar(props) {

let onChange = (event) => {
    console.log(event.target.value)
      setTitlePart(event.target.value)


}

    function sendTitlePart() {
        props.getFilmByTitlePart(titlePart)
        setTitlePart('')
    }
const [titlePart, setTitlePart] = useState('')
    return(
        <div className={'search_container'}>
            <div className="box">
                <div className="container-1">
                    <input type="search" id="search" placeholder="Поиск..." onChange={(event) => onChange(event)}/>
                    <span className="icon"><button onClick={() => sendTitlePart()} className="fa fa-search"><SearchIcon /></button></span>
                </div>
            </div>
        </div>
    )
}
export default SearchBar;