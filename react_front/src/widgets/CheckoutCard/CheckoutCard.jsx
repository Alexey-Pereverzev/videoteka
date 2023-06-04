import "./CheckoutCard.css";
import axios from "axios";
import {toast, ToastContainer} from "react-toastify";
import React from "react";

function CheckoutCard(props) {
    let displayCartNotification = (message) => {
        toast.success(message);
    };

    let sendPaymentRequest = async () => {
        try {
            const response = await axios.post('http://localhost:5555/cabinet/api/v1/orders')
                .then(response => {
                    displayCartNotification(response.data.value)
                },  function errorCallback(response) {
                    console.log(response)
                    let displayCartNotification = (message) => {
                        toast.error(message);
                    }
                    displayCartNotification(response.response.data.value)
                })
                .then(() => props.clearCart())

            displayCartNotification(response.response.value)
        } catch (e) {

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
                    <div className={'total-price_box'}>К оплате: <span>{props.totalPrice} руб</span></div>
                    <br/>

                </form>
                <button className={'checkout_btn'} onClick={() => sendPaymentRequest()}>Оплатить</button>
            </div>
            <ToastContainer
                position="top-right"
                autoClose={5000}
                hideProgressBar={false}
                newestOnTop={false}
                closeOnClick
                rtl={false}
                pauseOnFocusLoss
                draggable
                pauseOnHover
                theme="dark"
            />
        </div>
    )
}

export default CheckoutCard;