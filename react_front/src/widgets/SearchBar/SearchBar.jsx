import "./SearchBar.css"
import SearchIcon from '@mui/icons-material/Search';

function SearchBar(props) {


    return(
        <div className={'search_container'}>
            <div className="box">
                <form  className="container-1">
                    <span className="icon"><button className="fa fa-search"><SearchIcon onClick={(event) => props.onChange(event.target)}/></button></span>
                    <input type="search" id="search" placeholder="Поиск..." />
                </form>
            </div>
        </div>
    )
}
export default SearchBar;