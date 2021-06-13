import React from "react";
import { Card, Col, Row } from "react-bootstrap";
import ChangePasswordComponent from "../ChangePasswordComponent";

export default function UserInformationComponent({ userInformation }) {
  return (
    <Card>
      <Card.Body>
        <Row>
          <Col>
            <h1>{userInformation.username}</h1>
          </Col>
        </Row>
        <Row>
          <Col>Tillagd av: {userInformation.createdBy}</Col>
        </Row>
        <Row>
          <Col>
            <p>
              Kan du ta bort:{" "}
              <strong>{userInformation.ableToDelete ? "Ja" : "Nej"}</strong>
            </p>
          </Col>
          <Col>
            {userInformation.ableToDelete ? (
              <ChangePasswordComponent username={userInformation.username} />
            ) : null}
          </Col>
        </Row>
      </Card.Body>
    </Card>
  );
}
