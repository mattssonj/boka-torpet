import React, { useState } from "react";
import { Col, Nav } from "react-bootstrap";
import EditBookingModal from "./EditBookingModal";

export default function EditBookingButton({ booking }) {
  const [showModal, setShowModal] = useState(false);

  const hideModal = () => setShowModal(false);

  return (
    <Col>
      <Nav
        className="justify-content-center"
        activeKey="open"
        onSelect={() => setShowModal(true)}
      >
        <Nav.Item>
          <Nav.Link eventKey="open">Ändra</Nav.Link>
        </Nav.Item>
      </Nav>
      <EditBookingModal
        show={showModal}
        hideFunction={hideModal}
        booking={booking}
      />
    </Col>
  );
}
