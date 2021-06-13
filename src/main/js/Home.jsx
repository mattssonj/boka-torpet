import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";

import { Col, Container, Row } from "react-bootstrap";
import Logout from "./Logout.jsx";
import News from "./news/NewsComponent";
import Bookings from "./booking/BookingListComponent";
import CreateBooking from "./booking/CreatingBookingComponent";

export default function Home() {
  return (
    <Container>
      <Row>
        <News />
      </Row>
      <Row>
        <CreateBooking />
      </Row>
      <Row>
        <Bookings />
      </Row>
      <Row>
        <Col>
          <Logout />
        </Col>
      </Row>
    </Container>
  );
}
