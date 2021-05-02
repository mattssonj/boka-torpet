import React, {useState} from 'react';
import {Col, Form} from "react-bootstrap";
import Button from "react-bootstrap/Button";

const initialFormValues = {
    username: '',
    password: ''
}

export default function AddUserComponent() {

    const [addUserForm, setAddUserForm] = useState(initialFormValues)
    const clearForm = () => setAddUserForm(initialFormValues)

    const createUser = () => {
        console.log('Posting to backend')
        clearForm()
    }

    return (
        <Col>
            <Form>
                <Form.Group controlId="addUserUsername">
                    <Form.Label>Användarnamn</Form.Label>
                    <Form.Control type="text" placeholder="Användarnamn"
                                  onChange={e => setAddUserForm({...addUserForm, username: e.target.value})}
                                  value={addUserForm.username}/>
                    <Form.Text className="text-muted">
                        Det som kommer synas på bokningar och användas för inloggning.
                    </Form.Text>
                </Form.Group>

                <Form.Group controlId="addUserPassword">
                    <Form.Label>Lösenord</Form.Label>
                    <Form.Control type="password" placeholder="Lösenord"
                                  onChange={e => setAddUserForm({...addUserForm, password: e.target.value})}
                                  value={addUserForm.password}/>
                    <Form.Text className="text-muted">
                        Bara administratörer kan ändra eller återställa någons lösenord
                    </Form.Text>
                </Form.Group>
                <Button variant="success" type="submit">Skapa</Button>
            </Form>
        </Col>
    );
}