import React, { useState } from "react";

import { Col, Nav } from "react-bootstrap";
import BookingDetailsModal from "./BookingDetailsModal";

export default function BookingDetails({ booking }) {
  const [showModal, setShowModal] = useState(false);

  const hideModal = () => setShowModal(false);

  return (
    <Col>
      <Nav
        className="justify-content-end"
        activeKey="open"
        onSelect={() => setShowModal(true)}
      >
        <Nav.Item>
          <Nav.Link eventKey="open">Läs mer</Nav.Link>
        </Nav.Item>
      </Nav>
      <BookingDetailsModal
        show={showModal}
        hideFunction={hideModal}
        booking={booking}
      />
    </Col>
  );
}
