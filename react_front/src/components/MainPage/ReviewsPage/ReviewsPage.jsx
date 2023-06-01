import "./ReviewsPage.css"
import React, {useState} from "react"
import {Avatar, Divider, List, ListItem, ListItemAvatar, ListItemText, Typography} from "@mui/material";
import axios from "axios";

function ReviewsPage(props) {
    let getFilmIdReview = () => {
        try {
            axios.get('http://localhost:5555/catalog/api/v1/rating/list_all_grade_and_review_by_filmId', {
                params: {
                    filmId: props.filmId
                }
            })
                .then(response => response.data)
                .then(data => {
                    console.log(data.review)
                        setFilmReviews({
                            reviews: data.review,
                            id: data.user_id
                        })
                    }
                )
        } catch (e) {

        }
    }
    // let getFullNameReviewers = () => {
    //     axios.get('http://localhost:5555/auth/api/v1/users/get_fullname_by_id', {
    //         params: {
    //             userId:
    //         }
    // }
    const handleChange = (command) => {
        switch (command) {
            case '':
                props.setCommand(command)
                break
            case 'add_review':
                props.setCommand(command)
                break
            default:
                return null
        }
    }
    getFilmIdReview()
    const [fullName, setFullName] = useState('')
    const [filmReviews, setFilmReviews] = useState([]);
    return (
        <div className={'review-page'}>
            <List sx={{width: '100%', maxWidth: 360, bgcolor: 'background.paper'}}>
                <ListItem alignItems="flex-start">
                    <ListItemAvatar>
                        <Avatar alt="Remy Sharp" src="https://mui.com/static/images/avatar/3.jpg"/>
                    </ListItemAvatar>
                    <ListItemText
                        primary={props.title}
                        secondary={
                            <React.Fragment>
                                <Typography
                                    sx={{display: 'inline'}}
                                    component="span"
                                    variant="body2"
                                    color="text.primary"
                                >
                                    Ali Connors
                                </Typography>
                                {filmReviews.review?.map((txt) => (
                                    <span>{txt}</span>
                                ))}
                            </React.Fragment>
                        }
                    />
                </ListItem>
                <Divider variant="inset" component="li"/>
            </List>
            <div className={'review-page__btn_group'}>
                <div className={'button-back_box'}>
                    <button onClick={() => handleChange('')}>Вернуться к фильму</button>
                </div>
                <div className={'button-back_box'}>
                    <button onClick={() => handleChange('add_review')}>Написать отзыв</button>
                </div>
            </div>
        </div>
    )

}

export default ReviewsPage