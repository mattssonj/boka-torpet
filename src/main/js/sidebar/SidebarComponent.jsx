import { Col, Container, Nav, Navbar, Row } from "react-bootstrap";
import React from "react";
import { Link } from "react-router-dom";
import Logout from "./Logout.jsx";

export default function SidebarComponent() {
  return (
    <Container>
      <Row>
        <Col>
          <Navbar collapseOnSelect expand="md" sticky>
            <Container>
              <Navbar.Toggle aria-controls="responsive-navbar-nav" />
              <Navbar.Brand className="d-block d-md-none">
                Boka torpet
              </Navbar.Brand>
              {/*Navbar.Brand Boka Torpet Only visible when collapsed*/}
              <Navbar.Collapse id="responsive-navbar-nav">
                <Nav
                  className="flex-column"
                  variant="pills"
                  defaultActiveKey="/"
                >
                  <Nav.Link as={Link} to="/">
                    Hem
                  </Nav.Link>
                  <Nav.Link as={Link} to="/path/bookings">
                    Bokningar
                  </Nav.Link>
                  <Nav.Link as={Link} to="/path/messages">
                    Meddelanden
                  </Nav.Link>
                  <Nav.Link as={Link} to="/path/my_user_information">
                    Mitt Konto
                  </Nav.Link>
                  <Nav.Link as={Link} to="/path/admin">
                    Admin
                  </Nav.Link>
                  <Nav.Link as={Logout} />
                </Nav>
              </Navbar.Collapse>
            </Container>
          </Navbar>
        </Col>
      </Row>
    </Container>
  );
}
