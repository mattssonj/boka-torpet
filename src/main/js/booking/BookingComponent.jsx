import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";

import { Container, Row } from "react-bootstrap";
import CreateBooking from "./CreatingBookingComponent";
import Bookings from "./BookingListComponent";

export default function BookingComponent() {
  return (
    <Container>
      <Row>
        <CreateBooking />
      </Row>
      <Row>
        <Bookings />
      </Row>
    </Container>
  );
}
