import { Col, Container, Row } from "react-bootstrap";
import React from "react";
import MyUserInformationForm from "./MyUserInformationForm";

export default function MyUserInformationComponent() {
  return (
    <Container>
      <Row>
        <Col>
          <MyUserInformationForm />
        </Col>
      </Row>
    </Container>
  );
}
