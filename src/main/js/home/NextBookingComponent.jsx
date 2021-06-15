import React, { useEffect, useState } from "react";

import { Col, Container, Row } from "react-bootstrap";
import { Link } from "react-router-dom";
import backendClient from "../common/BackendClient";
import BookingListRow from "../booking/BookingListElement";

export default function NextBookingComponent() {
  const [booking, setBooking] = useState(null);

  useEffect(() => {
    backendClient.getBookings().then((bookings) => {
      if (!isEmpty(bookings.ongoing)) {
        setBooking(bookings.ongoing[0]);
      } else if (!isEmpty(bookings.upcoming)) {
        setBooking(bookings.upcoming[0]);
      }
    });
  }, []);

  const isEmpty = (list) => !Array.isArray(list) || !list.length;

  return (
    <Container>
      <Row>
        <Col>
          <h2>Nuvarande eller nästa bokning</h2>
        </Col>
      </Row>
      <Row>
        <Col>{booking ? <BookingListRow booking={booking} /> : null}</Col>
      </Row>
      <br />
      <Row>
        <Col>Se alla bokningar</Col>
        <Col>
          <Link
            className="btn btn-outline-dark btn-sm float-right"
            role="button"
            to="/path/bookings"
          >
            Till bokningarna
          </Link>
        </Col>
      </Row>
    </Container>
  );
}
