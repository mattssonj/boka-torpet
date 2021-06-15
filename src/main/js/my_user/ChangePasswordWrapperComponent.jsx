import { Col, Container, Row } from "react-bootstrap";
import ChangePasswordComponent from "../common/ChangePasswordComponent.jsx";
import React, { useEffect, useState } from "react";
import backendClient from "../common/BackendClient";

export default function ChangePasswordWrapperComponent() {
  const [loggedInUsername, setLoggedInUsername] = useState("");

  useEffect(() => {
    backendClient
      .getCurrentLoggedInUser()
      .then((user) => setLoggedInUsername(user.username))
      .catch((_) => {});
  });

  return (
    <Container>
      <Row>
        <Col>
          <h1>Byt lösenord</h1>
        </Col>
      </Row>
      <Row>
        <ChangePasswordComponent username={loggedInUsername} />
      </Row>
    </Container>
  );
}
