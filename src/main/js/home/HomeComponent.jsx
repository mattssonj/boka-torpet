import React from "react";

import { Col, Container, Row } from "react-bootstrap";

export default function HomeComponent() {
  return (
    <Container>
      <Row>
        <Col>
          <h1>
            Här kan du se en sammanfattning av vad som händer på torpet just nu
          </h1>
        </Col>
      </Row>
      <Row>
        <Col>
          <h2>Senaste meddelandet</h2>
        </Col>
        <Col>
          <p>Något coolt meddelande</p>
        </Col>
      </Row>
      <Row>
        <Col>
          <h2>På Torpet just nu eller nästkommande bokning</h2>
        </Col>
        <Col>Någon bokning</Col>
      </Row>
    </Container>
  );
}
