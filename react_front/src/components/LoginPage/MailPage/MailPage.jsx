import "./MailPage.css"

const MailPage = (props) => {
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

  function sendEmail() {
    handleChange('code')
  }

  return(
      <div className={'mail_page__container'}>
        Ввод почты и отправка
      <button onClick={() => sendEmail()}>Отправить</button>
      </div>
  )
}

export default MailPage