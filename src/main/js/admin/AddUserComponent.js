import React, {useState} from 'react';
import {Col, Form, Row} from "react-bootstrap";
import Button from "react-bootstrap/Button";
import Axios from "axios";
import {toaster} from "../common/Toaster";

const initialFormValues = {
    username: '',
    password: ''
}

export default function AddUserComponent() {

    const [addUserForm, setAddUserForm] = useState(initialFormValues)
    const clearForm = () => setAddUserForm(initialFormValues)

    const createUser = () => {
        if (addUserForm.username === '' || addUserForm.password === '') return
        Axios.post("/api/admin/users", addUserForm).then(response => {
            toaster.success("Användare: " + addUserForm.username + ' skapad')
            clearForm()
        }).catch(error => {
            toaster.warning("Något gick fel: " + error.response.data.message)
        })
    }

    return (
        <Col>
            <Form>
                <Row>
                    <Col>
                        <Form.Group controlId="addUserUsername">
                            <Form.Label>Användarnamn</Form.Label>
                            <Form.Control type="text" placeholder="Användarnamn"
                                          onChange={e => setAddUserForm({...addUserForm, username: e.target.value})}
                                          value={addUserForm.username}/>
                            <Form.Text className="text-muted">
                                Det som kommer synas på bokningar och användas för inloggning.
                            </Form.Text>
                        </Form.Group>
                    </Col>
                    <Col>
                        <Form.Group controlId="addUserPassword">
                            <Form.Label>Lösenord</Form.Label>
                            <Form.Control type="password" placeholder="Lösenord"
                                          onChange={e => setAddUserForm({...addUserForm, password: e.target.value})}
                                          value={addUserForm.password}/>
                            <Form.Text>Lösenord måste vara minst 6 tecken och inte bara vara mellanrum</Form.Text>
                            <Form.Text className="text-muted">
                                Bara administratörer kan ändra eller återställa någons lösenord.
                            </Form.Text>
                        </Form.Group>
                    </Col>
                </Row>
                <Row>
                    <Col className="text-right">
                        <Button variant="success" onClick={createUser}>Skapa</Button>
                    </Col>
                </Row>

            </Form>
        </Col>
    );
}