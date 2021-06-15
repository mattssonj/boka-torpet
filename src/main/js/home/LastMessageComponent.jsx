import React, { useEffect, useState } from "react";

import { Col, Container, Row } from "react-bootstrap";
import { Link } from "react-router-dom";
import backendClient from "../common/BackendClient";
import Message from "../messages/Message";
import { exampleMessage } from "../common/ExampleData";

export default function LastMessageComponent() {
  const [lastMessage, setLastMessage] = useState(exampleMessage);

  useEffect(() => {
    backendClient.getLastMessage().then((message) => setLastMessage(message));
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
