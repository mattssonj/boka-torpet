import React, { useState } from "react";
import Axios from "axios";

import { Button, Col, Form, Modal, Row } from "react-bootstrap";
import { toaster } from "../common/Toaster";

const initialFormObject = {
  name: "testing",
  message: "message",
  startDate: "2021-12-12",
  endDate: "2021-12-13",
};

export default function BookingModal({ show, hideFunction }) {
  const [isBooking, setIsBooking] = useState(false);
  const [formValues, setFormValues] = useState(initialFormObject);

  const resetForm = () => setFormValues(initialFormObject);

  const createBooking = async () => {
    setIsBooking(true);
    postRequest()
      .then((response) => {
        setIsBooking(false);
        resetForm();
        toaster.success(`Bokning "${response.data.name}" skapad`);
        hideFunction();
      })
      .catch((errorResp) => {
        console.log(errorResp.response);
        setIsBooking(false);
        toaster.error(
          `Något gick fel när du försökte spara bokningen: ${errorResp.response.data.message}`
        );
      });
  };

  const postRequest = async () => Axios.post("/api/bookings", formValues);

  return (
    <Modal show={show} onHide={hideFunction}>
      <Modal.Header>
        <Modal.Title>Skapa Bokning</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form>
          <Form.Group controlId="bookingName">
            <Form.Label>Bokningsnamn</Form.Label>
            <Form.Control
              type="text"
              onChange={(e) =>
                setFormValues({ ...formValues, name: e.target.value })
              }
              value={formValues.name}
            />
            <Form.Text className="text-muted">
              Namnet skulle kunna beskriva bokning, Ex. Midsommarfest eller
              liknande.
            </Form.Text>
          </Form.Group>
          <Form.Group controlId="bookingMessage">
            <Form.Label>Bokningsmeddelande</Form.Label>
            <Form.Control
              type="text"
              onChange={(e) =>
                setFormValues({ ...formValues, message: e.target.value })
              }
              value={formValues.message}
              placeholder="Namn på bokning"
            />
            <Form.Text className="text-muted">
              En text som förklarar vad som ev. ska hända, Ex. "Vi är 10 pers
              som ska ha fest"
            </Form.Text>
          </Form.Group>
          <Row>
            <Col>
              <Form.Group controlId="bookingStartDate">
                <Form.Label>Start</Form.Label>
                <Form.Control
                  type="date"
                  onChange={(e) =>
                    setFormValues({ ...formValues, startDate: e.target.value })
                  }
                  value={formValues.startDate}
                />
              </Form.Group>
            </Col>
            <Col>
              <Form.Group controlId="bookingEndDate">
                <Form.Label>Slut</Form.Label>
                <Form.Control
                  type="date"
                  onChange={(e) =>
                    setFormValues({ ...formValues, endDate: e.target.value })
                  }
                  value={formValues.endDate}
                />
              </Form.Group>
            </Col>
          </Row>
        </Form>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={hideFunction}>
          Stäng
        </Button>
        <Button
          variant="primary"
          onClick={!isBooking ? createBooking : null}
          disabled={isBooking}
        >
          {isBooking ? "Bokar..." : "Boka"}
        </Button>
      </Modal.Footer>
    </Modal>
  );
}
