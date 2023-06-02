import style from "./SpeechBubbles.module.css"
import {useState} from "react";
import axios from "axios";

const SpeechBubbles = (props) => {
    const getNameById = async () => {
        const response = await axios.get('http://localhost:5555/auth/api/v1/users/get_fullname_by_id', {
            params: {
                userId: props.userId
            }
        })
            .then(response => response.data)
            .then(data => {
                console.log(data)
                setName(data)
            })
    }
    const [name, setName] = useState('')
  return(
      <div className={style.speech_container}>
          <div className={style.bubble}>
              <span >"</span>
              <span>{props.message}</span>
              <span>"</span>
              <i>{name}</i>
          </div>
      </div>
  )
}
export default SpeechBubbles