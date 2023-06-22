import "./ListCard.css"

const ListCard = (props) => {
  return(
      <div className={'list_card__container'}
           onDragStart={props.onDragStart}
           onDragLeave={props.onDragEnd}
           onDragEnd={props.onDragEnd}
           onDragOver={props.onDragOver}
           onDrop={props.onDrop}
           draggable={true}>
          <div className={'list_card__content'}>
              <span className={'list_card__text'}>{props.msg}</span>
          </div>
      </div>
  )
}
export default ListCard