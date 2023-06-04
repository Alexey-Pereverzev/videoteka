import style from "./ModeratorPage.module.css"
import SpeechBubbles from "../../../widgets/SpeechBubbles/SpeechBubbles";
import axios from "axios";
import {useEffect, useState} from "react";
///list_all_grade_and_review_is_not_moderate
function ModeratorPage() {
   const getMessagesOnModerate = async () => {
       try {
           return await axios.get('http://localhost:5555/catalog/api/v1/rating/list_all_grade_and_review_is_not_moderate')
               .then((response) =>
                   response.data)
               .then((data) => {
                   console.log(data)
                   setPosts(data)
               })
       }catch (e) {
           console.log(e)
       }

    }
    useEffect(() => {
        getMessagesOnModerate()
    },[])
    const [posts, setPosts] = useState([])
    return(
        <div className={style.moderator_container}>
            {posts.map((post) => (
                <div className={style.message_box}>
                    <SpeechBubbles post={post.review}
                                   userId={post.user_id}
                    />
                    <div className={style.btn_box}>
                        <button className={style.apply_btn}>Опубликовать</button>
                        <button className={style.deny_btn}>Отказать</button>
                    </div>

                </div>
            ))}

        </div>
    )
}
export default ModeratorPage