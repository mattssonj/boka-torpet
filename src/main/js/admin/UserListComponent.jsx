import React, { useEffect, useState } from "react";
import { useHistory } from "react-router-dom";
import { Button, Col, ListGroup, Row, Tab } from "react-bootstrap";
import Axios from "axios";
import toaster from "../common/Toaster";
import UserInformationComponent from "./UserInformationComponent";

const FORBIDDEN = 403;

export default function UserListComponent(showAll) {
  const errorMessage = showAll
    ? "Kunde inte hämta alla användare. Error "
    : "Kunde inte hämta dina användare. Error ";
  const forbiddenMessage =
    "Bara admins kan använda denna vy. Kontakta den som skapade ditt konto för frågor.";

  const [users, setUsers] = useState([]);

  const history = useHistory();

  useEffect(() => {
    updateUsers();
  }, []);

  const updateUsers = () => {
    Axios.get("/api/admin/users")
      .then((response) => setUsers(response.data))
      .catch((error) => {
        if (error.response.status === FORBIDDEN) handleForbidden();
        else toaster.error(errorMessage + error.response.data.message);
      });
  };

  const handleForbidden = () => {
    toaster.error(forbiddenMessage);
    history.push("/");
  };

  const tabContent = (user) => {
    const eventKey = `#${user.username}`;
    return (
      <Tab.Pane eventKey={eventKey}>
        <UserInformationComponent key={eventKey} userInformation={user} />
      </Tab.Pane>
    );
  };

  return (
    <Col>
      <Row>
        <Col>
          <Button variant="outline-secondary" onClick={updateUsers}>
            Uppdatera listan
          </Button>
        </Col>
      </Row>
      <br />
      <Row>
        <Col>
          <Tab.Container id="list-group-tabs-example" defaultActiveKey="#link1">
            <Row>
              <Col sm={4}>
                <ListGroup>
                  {users.map((user) => (
                    <ListGroup.Item action href={`#${user.username}`}>
                      {user.username}
                    </ListGroup.Item>
                  ))}
                </ListGroup>
              </Col>
              <Col sm={8}>
                <Tab.Content>
                  {users.map((user) => tabContent(user))}
                </Tab.Content>
              </Col>
            </Row>
          </Tab.Container>
        </Col>
      </Row>
    </Col>
  );
}
