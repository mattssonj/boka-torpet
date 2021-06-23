import React, { useEffect, useState } from "react";
import Axios from "axios";

import { Button, Col, Row } from "react-bootstrap";
import BookingListRow from "./BookingListElement";
import backendClient from "../common/clients/BackendClient";

const initialShowing = {
  ongoing: [],
  upcoming: [],
};

export default function Bookings() {
  const [bookings, setBookings] = useState(initialShowing);
  const [showing, setShowing] = useState(initialShowing);
  const [showAll, setShowAll] = useState(true);
  const [loggedInUsername, setLoggedInUsername] = useState(null);

  useEffect(() => {
    backendClient
      .getBookings()
      .then((bookings) => {
        setBookings(bookings);
        setShowing(bookings);
      })
      .catch((_) => {});
    getLoggedInUsername();
  }, []);

  const updateBookings = () => {
    backendClient.getBookings().then((bookings) => {
      showAll ? showAllBookings(bookings) : showLoggedInBookings(bookings);
      setBookings(bookings);
    });
  };

  const showAllBookings = (newBookings) => {
    newBookings ? setShowing(newBookings) : setShowing(bookings);
    setShowAll(true);
  };

  const showLoggedInBookings = (newBookings) => {
    const bookingsToShow = newBookings || bookings;
    const bookerFilter = (bookings) =>
      bookings.filter((booking) => booking.booker === loggedInUsername);
    setShowing({
      ongoing: bookerFilter(bookingsToShow.ongoing),
      upcoming: bookerFilter(bookingsToShow.upcoming),
    });
    setShowAll(false);
  };

  const getLoggedInUsername = () => {
    backendClient
      .getCurrentLoggedInUser()
      .then((userInfo) => setLoggedInUsername(userInfo.username))
      .catch((_) => {
        /* Do nothing */
      });
  };

  const mapListItems = (list, message) => {
    const listItems = list.map((booking) => (
      <div key={booking.id}>
        <BookingListRow booking={booking} username={loggedInUsername} />
        <br />
      </div>
    ));
    return listItems.length > 0 ? listItems : message;
  };

  return (
    <Col>
      <br />
      <Row className="justify-content-md-center">
        <Col xs="auto">
          <Button variant="outline-secondary" onClick={updateBookings}>
            Uppdatera listan
          </Button>
        </Col>
        <Col xs="auto">
          <Button
            variant={!showAll ? "secondary" : "outline-secondary"}
            onClick={() => showLoggedInBookings(null)}
          >
            Visa mina
          </Button>
        </Col>
        <Col xs="auto">
          <Button
            variant={showAll ? "secondary" : "outline-secondary"}
            onClick={() => showAllBookings(null)}
          >
            Visa alla
          </Button>
        </Col>
      </Row>
      <Row>
        <Col>
          <h1>På torpet just nu</h1>
        </Col>
      </Row>
      <Row>
        <Col>
          {mapListItems(showing.ongoing, "Ingen verkar vara där just nu")}
        </Col>
      </Row>
      <Row>
        <Col>
          <h1>Kommande</h1>
        </Col>
      </Row>
      <Row>
        <Col>
          {mapListItems(showing.upcoming, "Igen har bokat att de ska åka upp")}
        </Col>
      </Row>
    </Col>
  );
}
