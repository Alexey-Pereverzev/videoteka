import "./ProfilePage.css";

function ProfilePage() {
    return (
        <div className={'profile-page__container'}>
            <div className="login-box">
                <h2>Изменить личные данные</h2>
                <form>
                    <div className={'box__container'}>
                        <div className="user-box">
                            <input type="text" name="" required=""/>
                            <label>Username</label>
                        </div>
                        <div className="user-box">
                            <input type="password" name="" required=""/>
                            <label>Password</label>
                        </div>
                    </div>
                    <div>
                        <div className="user-box">
                            <input type="text" name="" required=""/>
                            <label>Username</label>
                        </div>
                        <div className="user-box">
                            <input type="password" name="" required=""/>
                            <label>Password</label>
                        </div>
                    </div>

                    <a href="/">
                        Submit
                    </a>
                </form>
            </div>
        </div>

    )
}

export default ProfilePage