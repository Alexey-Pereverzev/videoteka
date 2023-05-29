import style from "./RedactorPage.module.css"

// Long id;
// String title;
// Integer premierYear;
// String description;
// String imageUrlLink;
// List<String> genre;
// List<String> country;
// List<String> director;
// Integer rentPrice;
// Integer salePrice;
function RedactorPage() {
    return(
        <div className={style.redactor_container}>Редактирование карточки фильма</div>
    )
}
export default RedactorPage