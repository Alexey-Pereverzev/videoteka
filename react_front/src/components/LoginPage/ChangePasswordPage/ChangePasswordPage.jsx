import "./ChangePasswordPage.css"

const ChangePasswordPage = (props) => {
  const handleChange = (command) => {
    switch (command) {
      case '':
        props.setCommand(command)
        break
      case 'add_review':
        props.setCommand(command)
        break
      default:
        return null
    }
  }
  return(
      <div className={'change_password__container'}>Смена пароля</div>
  )
}
export default ChangePasswordPage