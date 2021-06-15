import React, { useState } from "react";

import { Alert, Button, Col } from "react-bootstrap";
import BookingModal from "./BookingModal";

export default function CreateBooking() {
  const [showModal, setShowModal] = useState(false);
  const [showAlert, setShowAlert] = useState({
    show: false,
    error: false,
    message: {},
  });

  const hideModal = () => setShowModal(false);
  const hideAlert = () =>
    setShowAlert({ show: false, error: false, message: {} });
  const presentAlert = (newMessage, error) => {
    setShowAlert({ show: true, error, message: newMessage });
    setShowModal(false);
  };

  return (
    <Col>
      <Alert
        show={showAlert.show}
        variant={showAlert.error ? "danger" : "success"}
      >
        <p>{JSON.stringify(showAlert.message)}</p>
        <div className="d-flex justify-content-end">
          <Button onClick={hideAlert} variant="outline-success">
            Ok!
          </Button>
        </div>
      </Alert>
      <Button
        variant="primary"
        size="lg"
        block
        onClick={() => setShowModal(true)}
      >
        Boka
      </Button>
      <BookingModal
        show={showModal}
        hideFunction={hideModal}
        bookingComplete={presentAlert}
      />
    </Col>
  );
}
