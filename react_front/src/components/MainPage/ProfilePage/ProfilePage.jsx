import "./ProfilePage.css";

function ProfilePage() {
    return(
        <div className="container">
            <div>
                <h4 className="title">Underlined Inputs</h4>
                <form>
                    <div className="omrs-input-group">
                        <label className="omrs-input-underlined">
                            <input required/>
                                <span className="omrs-input-label">Normal</span>
                                <span className="omrs-input-helper">Helper Text</span>
                        </label>
                    </div>
                </form>
            </div>
        </div>
    )
}

export default ProfilePage