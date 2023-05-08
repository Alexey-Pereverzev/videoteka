import "./SearchBar.css"
function SearchBar(props) {
    return(
        <div className={'search_container'}>
            <div className="box">
                <div className="container-1">
                    <span className="icon"><i className="fa fa-search"></i></span>
                    <input type="search" id="search" placeholder="Search..." onChange={props.onChange}/>
                </div>
            </div>
        </div>
    )
}
export default SearchBar;