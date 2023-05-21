import "./ProfilePage.css";
import axios from "axios";

function ProfilePage() {
   let sendChangePasswordRequest = (username,
                                 oldPassword,
                                 newPassword,
    ) => {
       alert('Поздравляем! Пароль изменён, новый пароль: ' + newPassword)
        // return axios
        //     .post("http://localhost:5555/auth/api/v1/reg/register", {
        //         username,
        //         oldPassword,
        //         newPassword,
        //     })
        //     .then(response => response.data)
    }
    return (
        <div className={'profile-page__container'}>
            <div className="login-box">
                <h2>Изменить личные данные</h2>
                <form method={'post'} onSubmit={() => sendChangePasswordRequest()}>
                    <div className={'box__container'}>
                        <div className="user-box">
                            <input type="text" name="oldPassword" required=""/>
                            <label>Старый пароль</label>
                        </div>
                        <div className="user-box">
                            <input type="password" name="newPassword" required=""/>
                            <label>Новый пароль</label>
                        </div>
                    </div>

                    <button type={'submit'}>Отправить</button>
                </form>
            </div>
        </div>

    )
}

export default ProfilePage