import "./CheckoutCard.css";
import axios from "axios";

function CheckoutCard(props) {

    let sendPaymentRequest = async () => {
        try {
            const response = await axios.post('http://localhost:5555/cabinet/api/v1/orders')
            console.log("Ответ метода sendPaymentRequest: " + response)
            props.clearCart()
        } catch (e) {
            alert("Метод sendPaymentRequest: " + e)
        }
    }

    return(
        <div className={'checkout-page__container'}>
            <div className="checkout-box">
                <h2>Данные для оплаты</h2>
                <form>
                    <div className={'box__container'}>
                        <div className="user-box">
                            <input type="text" name="cartHolderName" placeholder={'IVAN IVANOV'} required=""/>
                            <label>Имя владельца карты</label>
                        </div>
                        <div className="user-box">
                            <input type="number" name="cartNumber" placeholder={'2202 2016...'} required=""/>
                            <label>Номер карты</label>
                        </div>
                    </div>
                    <div className={'checkout__details'}>
                        <div className="details__box">
                            <input type="text" name="exp" placeholder={'2023/2025'} required=""/>
                            <label>Дата</label>
                        </div>
                        <div className="details__box">
                            <input type="number" name="cvc" placeholder={'577'} required=""/>
                            <label>CVC</label>
                        </div>
                    </div>
                    <div className={'total-price_box'}>К оплате: <span>{props.totalPrice}</span></div>
                    <br/>
                    <button onClick={() => sendPaymentRequest()}>Отправить</button>
                </form>
            </div>
        </div>
    )
}

export default CheckoutCard;