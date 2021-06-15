import React from "react";

import { Col, Container, Row } from "react-bootstrap";
import Logout from "../sidebar/Logout.jsx";
import AddUserComponent from "./AddUserComponent";
import UserListComponent from "./UserListComponent";

const message =
  "Här kan man lägga till och ta bort användare. Du kan också hjälpa de användare du har lagt till att byta lösenord.";

export default function AdminComponent() {
  return (
    <Container>
      <Row>
        <Col>
          <h1>Admin Panel</h1>
        </Col>
      </Row>
      <Row>
        <Col>
          <p>{message}</p>
        </Col>
      </Row>
      <Row>
        <AddUserComponent />
      </Row>
      <br />
      <Row>
        <UserListComponent showAll />
      </Row>
    </Container>
  );
}
