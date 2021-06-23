import React from "react";

import { Col, Container, Row } from "react-bootstrap";
import LastMessageComponent from "./LastMessageComponent";
import NextBookingComponent from "./NextBookingComponent";

export default function HomeComponent() {
  return (
    <Container>
      <Row>
        <Col>
          <h1>Boka torpet</h1>
          <hr />
        </Col>
      </Row>
      <Row>
        <LastMessageComponent />
      </Row>
      <hr />
      <Row>
        <NextBookingComponent />
      </Row>
    </Container>
  );
}
