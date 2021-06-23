import React, { useState } from "react";
import Axios from "axios";
import { Button, Col, Form, Modal, Row } from "react-bootstrap";
import toaster from "../common/Toaster";

const emptyForm = {
  name: "",
  message: "",
  startDate: "",
  endDate: "",
};

export default function EditBookingModal({ show, hideFunction, booking }) {
  const [isBooking, setIsBooking] = useState(false);
  const [formValues, setFormValues] = useState(booking);

  const updateBooking = async () => {
    setIsBooking(true);
    await Axios.put(`/api/bookings/${formValues.id}`, formValues)
      .then((response) => {
        setFormValues(response.data);
        hideFunction();
        toaster.success(
          `Bokning "${response.data.name}" uppdaterad. Glöm inte att uppdatera listan över bokningar`
        );
      })
      .catch((error) => {
        console.log(error.response);
        toaster.error(
          `Något gick fel när du försökte spara bokningen: ${error.response.data.message}`
        );
      });
    setIsBooking(false);
  };

  const deleteBooking = async () => {
    console.log(`Deleting Booking with Id ${formValues.id}`);
    setIsBooking(true);
    await Axios.delete(`/api/bookings/${formValues.id}`)
      .then((response) => {
        toaster.success(`Bokning "${formValues.name}" borttagen`);
        setFormValues(emptyForm);
        hideFunction();
      })
      .catch((error) => {
        toaster.error(
          `Någon gick fel när du försökte ta bort bokningen: ${error.response.data.message}`
        );
      });
    setIsBooking(false);
  };

  return (
    <Modal show={show} onHide={hideFunction}>
      <Modal.Header>
        <Modal.Title>Uppdatera Bokning</Modal.Title>
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
      <Modal.Footer className="justify-content-between">
        <Button
          variant="danger"
          className="float-left"
          onClick={!isBooking ? deleteBooking : null}
          disabled={isBooking}
        >
          Tar bort direkt
        </Button>
        <div>
          <Button variant="secondary" onClick={hideFunction}>
            Stäng
          </Button>
          <Button
            variant="success"
            className="ml-1"
            onClick={!isBooking ? updateBooking : null}
            disabled={isBooking}
          >
            {isBooking ? "Uppdaterar..." : "Uppdatera"}
          </Button>
        </div>
      </Modal.Footer>
    </Modal>
  );
}
