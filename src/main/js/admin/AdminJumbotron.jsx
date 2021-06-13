import React from "react";
import { Link } from "react-router-dom";
import { Col, Jumbotron, Row } from "react-bootstrap";

const message =
  "Här kan man lägga till och ta bort användare. Du kan också hjälpa de användare du har lagt till att byta lösenord.";

export default function AdminJumbotron() {
  return (
    <Col>
      <Jumbotron>
        <Row>
          <Col>
            <h1>Admin Panel</h1>
          </Col>
        </Row>
        <Row>
          <Col>
            <p>{message}</p>
          </Col>
        </Row>
        <Row />
        <Row>
          <Col className="text-right">
            <Link className="btn btn-outline-dark btn-sm" role="button" to="/">
              Tillbaka
            </Link>
          </Col>
        </Row>
      </Jumbotron>
    </Col>
  );
}
