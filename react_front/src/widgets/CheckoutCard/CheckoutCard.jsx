import "./CheckoutCard.css";
import axios from "axios";

function CheckoutCard() {

    function sendPaymentRequest() {
        return axios
            .post("http://localhost:5555/auth/api/v1/reg/register", {

            })
            .then(response => response.data)
    }

    return(
        <div className={'checkout-page__container'}>
            <div className="checkout-box">
                <h2>Изменить личные данные</h2>
                <form method={'post'} onSubmit={() => sendPaymentRequest()}>
                    <div className={'box__container'}>
                        <div className="user-box">
                            <input type="text" name="oldPassword" required=""/>
                            <label>Имя владельца карты</label>
                        </div>
                        <div className="user-box">
                            <input type="number" name="newPassword" required=""/>
                            <label>Номер карты</label>
                        </div>
                    </div>
                    <button type={'submit'}>Отправить</button>
                </form>
            </div>
        </div>
    )
}

export default CheckoutCard;