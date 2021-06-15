import { Col, Container, Row } from "react-bootstrap";
import React from "react";
import MyUserInformationForm from "./MyUserInformationForm";
import ChangePasswordWrapperComponent from "./ChangePasswordWrapperComponent";

export default function MyUserInformationComponent() {
  return (
    <Container>
      <Row>
        <Col>
          <MyUserInformationForm />
        </Col>
      </Row>
      <Row>
        <Col>
          <hr />
        </Col>
      </Row>
      <Row>
        <Col>
          <ChangePasswordWrapperComponent />
        </Col>
      </Row>
    </Container>
  );
}
