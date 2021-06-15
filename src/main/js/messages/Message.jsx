import React from "react";
import { Col, Container, Row } from "react-bootstrap";

export default function Message({ message }) {
  return (
    <Container>
      <div className="border d-inline-block bg-light p-3 rounded">
        <Row>
          <Col>
            <p>{message.message}</p>
          </Col>
        </Row>
        <Row>
          <Col className="text-right">
            <small>
              Skrivet {message.written} av {message.writer}
            </small>
          </Col>
        </Row>
      </div>
    </Container>
  );
}
