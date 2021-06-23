import React, { useEffect, useState } from "react";

import { Col, Container, Row } from "react-bootstrap";
import { Link } from "react-router-dom";
import Message from "../messages/Message";
import exampleMessage from "../common/ExampleData";
import messageClient from "../common/clients/MessageClient";

export default function LastMessageComponent() {
  const [lastMessage, setLastMessage] = useState(exampleMessage);

  useEffect(() => {
    messageClient
      .getLastMessage()
      .then((message) => setLastMessage(message))
      .catch((_) => {});
  }, []);

  return (
    <Container>
      <Row>
        <Col>
          <h2>Senaste meddelandet</h2>
        </Col>
      </Row>
      <Row>
        {lastMessage ? (
          <Message message={lastMessage} />
        ) : (
          <Col>
            <p>Något gick fel!</p>
          </Col>
        )}
      </Row>
      <br />
      <Row>
        <Col>Se alla meddelandena</Col>
        <Col>
          <Link
            className="btn btn-outline-dark btn-sm float-right"
            role="button"
            to="/path/messages"
          >
            Till meddelandena
          </Link>
        </Col>
      </Row>
    </Container>
  );
}
