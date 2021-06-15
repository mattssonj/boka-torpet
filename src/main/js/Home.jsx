import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";

import { Col, Row } from "react-bootstrap";
import Logout from "./Logout.jsx";
import News from "./messages/NewsComponent";
import Bookings from "./booking/BookingListComponent";
import CreateBooking from "./booking/CreatingBookingComponent";

export default function Home() {
  return (
    <Col>
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
    </Col>
  );
}
