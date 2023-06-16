import './AddDirectorPage.css'
import React, {useState} from "react";
import axios from "axios";
import {FormControl} from "@mui/material";
import {clear} from "@testing-library/user-event/dist/clear";

const AddDirectorPage = (props) => {
    let addNewDirector = () => {
        axios.post('http://localhost:5555/catalog/api/v1/director/new', {
            firstName: firstName,
            lastName: lastName
        }).then(r => r)
            .then(() => {
                props.getDirectors()

            }, () => setFirstName(''))
    }
    let onChangeFistHandler = (event) => {
        setFirstName(event.target.value)
    }
    let onChangeLastHandler = (event) => {
        setLastName(event.target.value)
    }
    const [firstName, setFirstName] = useState("")
    const [lastName, setLastName] = useState("")
    return (
        <div className={'add_director__container'}>
            <div className={'add_country__title'}>Добавить режиссёра</div>
            <FormControl sx={{m: 1, minWidth: 120}} size="small" focused={false}>
                <div className={'input_container'}>
                    <input type="text" required="" value={firstName || ''} onChange={(event) => onChangeFistHandler(event)}
                           onfocusout={clear}/>
                    <label>Имя</label>
                </div>
                <div className={'input_container'}>
                    <input type="text" required="" value={firstName || ''} onChange={(event) => onChangeLastHandler(event)}
                           onfocusout={clear}/>
                    <label>Фамилия</label>
                </div>
                <div className={'add_country__send_btn'}>
                    <button onClick={() => addNewDirector()}>Сохранить режиссёра</button>
                </div>
            </FormControl>
        </div>
    )
}

export default AddDirectorPage