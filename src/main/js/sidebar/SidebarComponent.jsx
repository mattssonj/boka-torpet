import { Container, Nav, Navbar } from "react-bootstrap";
import React from "react";
import { Link } from "react-router-dom";

export default function SidebarComponent() {
  return (
    <Navbar collapseOnSelect expand="md" sticky>
      <Container>
        <Navbar.Brand className="d-block d-md-none">Boka torpet</Navbar.Brand>{" "}
        {/*Only visible when collapsed*/}
        <Navbar.Toggle aria-controls="responsive-navbar-nav" />
        <Navbar.Collapse id="responsive-navbar-nav">
          <Nav className="flex-column" variant="pills" defaultActiveKey="/">
            <Nav.Link as={Link} to="/">
              Home
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
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}
