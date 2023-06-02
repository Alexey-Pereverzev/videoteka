import style from "./ModeratorPage.module.css"
import SpeechBubbles from "../../../widgets/SpeechBubbles/SpeechBubbles";
import axios from "axios";
import {useEffect, useState} from "react";
///list_all_grade_and_review_is_not_moderate
function ModeratorPage() {
   const getMessagesOnModerate = async () => {
        const response = await axios.get('http://localhost:5555/catalog/api/v1/rating/list_all_grade_and_review_is_not_moderate')
            .then(response => response.data)
            .then(data => {
                console.log(data)
                setPosts({
                    userId: data.userId,
                    message:data.review
                })
            })
    }
    useEffect(() => {
        getMessagesOnModerate()
    })
    const [posts, setPosts] = useState([])
    return(
        <div className={style.moderator_container}>
            {posts.map(post => (
                <div style={style.message_box}>
                    <SpeechBubbles post={post.message}
                                   userId={post.userId}
                    />
                    <button className={style.apply_btn}>Опубликовать</button>
                    <button className={style.deny_btn}>Отказать</button>
                </div>
            ))}

        </div>
    )
}
export default ModeratorPage