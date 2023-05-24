import './UsersPage.css';
import {
    MenuItem,
    Paper,
    Select,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TablePagination,
    TableRow
} from "@mui/material";
import axios from "axios";
import {useEffect, useState} from "react";
import DeleteIcon from "@mui/icons-material/Delete";
///api/v1/roles/update

function UsersPage(props) {

    let getAllUsers = () => {
        return axios.get("http://localhost:5555/auth/api/v1/users/list_all")
            .then(response => response.data)
            .then(data => setUsers(data))
    }
    // const [role, setRole] = useState('');
    const [users, setUsers] = useState([])
    useEffect(() => {
        getAllUsers();
    }, []);
    const handleChange = (event, whomChangeId) => {
        console.log(event.target)
        axios.put("http://localhost:5555/auth/api/v1/roles/update", {
            changeUserId: whomChangeId,
            role: event.target.value
        })
            .then(r => console.log("Работает handleChange:"+r))
    };

    let banToUser = async (userId) => {
        console.log(userId)
        try {
            const response = await axios.get('http://localhost:5555/auth/api/v1/users/delete',
                {
                    params: {
                        deleteUserId: userId
                    }
                }
            )
            console.log("Ответ метода banToUser: " + response)
            getAllUsers()
        } catch (e) {
            alert(e)
        }
    }
    return (
        <div className={'users_page'}>
            <div className={'users_page__title'}>
                Пользователи
            </div>
            <div className={'user_page__box'}>
                <TableContainer component={Paper}>
                    <Table stickyHeader={true} sx={{minWidth: 1050}} aria-label="sticky table">
                        <TableHead>
                            <TableRow>
                                <TableCell sx={{background: '#2b303b', color: 'white'}}>Псевдоним</TableCell>
                                <TableCell sx={{background: '#2b303b', color: 'white'}} align="right">Id</TableCell>
                                <TableCell sx={{background: '#2b303b', color: 'white'}} align="right">Имя</TableCell>
                                <TableCell sx={{background: '#2b303b', color: 'white'}} align="right">Фамилия</TableCell>
                                <TableCell sx={{background: '#2b303b', color: 'white'}} align="right">Адрес почты</TableCell>
                                <TableCell sx={{background: '#2b303b', color: 'white'}} align="right">Адрес доставки</TableCell>
                                <TableCell sx={{background: '#2b303b', color: 'white'}} align="right">Номер</TableCell>
                                <TableCell sx={{background: '#2b303b', color: 'white', width: 77}} align="center">Права</TableCell>
                                <TableCell sx={{background: '#2b303b', color: 'white'}} align="right">Статус</TableCell>
                                <TableCell sx={{background: '#2b303b', color: 'white', width: 50}} align="right"></TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {users.map((row) => (
                                <TableRow
                                    hover role="checkbox"
                                    tabIndex={-1}
                                    key={row.username}
                                    sx={{'&:last-child td, &:last-child th': {border: 0}}}
                                >
                                    <TableCell component="th" scope="row">
                                        {row.username}
                                    </TableCell>
                                    <TableCell align="right">{row.id}</TableCell>
                                    <TableCell align="right">{row.firstName}</TableCell>
                                    <TableCell align="right">{row.lastName}</TableCell>
                                    <TableCell align="right"><a href={`mailto:${row.email}`}>{row.email}</a></TableCell>
                                    <TableCell align="right">{row.address}</TableCell>
                                    <TableCell align="right">{row.phoneNumber}</TableCell>
                                    <TableCell align="left">
                                        <Select
                                            labelId="demo-simple-select-label"
                                            id="demo-simple-select"
                                            value={row.role}
                                            label={row.role}
                                            onChange={(event,_) => handleChange(event,row.id)}
                                        >
                                            <MenuItem value={'ROLE_USER'}>USER</MenuItem>
                                            <MenuItem value={'ROLE_ADMIN'}>ADMIN</MenuItem>
                                            <MenuItem value={'ROLE_MANAGER'}>MANAGER</MenuItem>
                                        </Select>
                                        {/*{row.role}*/}
                                    </TableCell>
                                    <TableCell align="right">
                                        {row.deleted ?
                                            <h4 className={'banned'}>бан</h4>
                                            :
                                            <h4 className={'active_user'}>активен</h4>
                                        }
                                    </TableCell>
                                    <TableCell align="right"><button className={'delete-user__btn'} onClick={() => banToUser(row.id)}><DeleteIcon/></button></TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
            </div>

        </div>
    )
}

export default UsersPage