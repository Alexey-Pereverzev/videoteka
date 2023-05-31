import "./SearchBar.css"
import SearchIcon from '@mui/icons-material/Search';

function SearchBar(props) {

let onChange = (event) => {
    console.log(event)
    props.getFilmByTitlePart(event.target)
}
    return(
        <div className={'search_container'}>
            <div className="box">
                <form  className="container-1">
                    <input type="search" id="search" placeholder="Поиск..." />
                    <span className="icon"><button className="fa fa-search"><SearchIcon onClick={(event) => onChange(event)}/></button></span>
                </form>
            </div>
        </div>
    )
}
export default SearchBar;