import React from "react";
import { Col, Container, Row } from "react-bootstrap";
import DateFormatter from "../common/DateFormatter";

export default function Message({ message }) {
  const multilineCssTag = {
    // This object is needed to style the <p> tag to handle \n to create new line
    whiteSpace: "pre-wrap",
  };

  return (
    <Container>
      <div className="border d-inline-block bg-light p-3 rounded">
        <Row>
          <Col>
            <p style={multilineCssTag}>{message.message}</p>
          </Col>
        </Row>
        <Row>
          <Col className="text-right">
            <small>
              Skrivet{" "}
              {message.createdAt ? DateFormatter(message.createdAt) : undefined}{" "}
              av {message.writer}
            </small>
          </Col>
        </Row>
      </div>
    </Container>
  );
}
