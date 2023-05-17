import "./OwnCard.css";
import {Button, Card, CardActions, CardContent, CardMedia, Typography} from "@mui/material";
import {useState} from "react";
import ModalWindow from "../ModalWindow/ModalWindow";

function OwnCard(props) {
    const [modalActive, setModalActive] = useState(false);
    return(
        <div className={'own-card__container'}>
            <Card sx={{ width: 200 }}>
                <CardMedia
                    sx={{ height: 170 }}
                    image={props.cover}
                    title={props.title}
                />
                <CardContent >
                    <Typography className={'own-card__title'} gutterBottom variant="h5" component="div">
                        {props.title}
                    </Typography>
                    <br/>
                    <Typography className={'own-card__description'} variant="body2" color="text.secondary">
                        {props.description}
                    </Typography>
                </CardContent>
                <CardActions>
                    <Button size="small" onClick={() => setModalActive(true)}>Смотреть</Button>
                </CardActions>
            </Card>
            <ModalWindow active={modalActive}
                         setActive={setModalActive}
            >
                <video controls autoPlay="autoplay">
                    <source src={'https://vk.com/video-170685099_456240752'}/>
                </video>

            </ModalWindow>
        </div>

    )
}

export default OwnCard;