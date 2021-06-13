import React, { useEffect, useState } from "react";
import Axios from "axios";

import { Button, Form, Modal } from "react-bootstrap";
import toaster from "../common/Toaster";

const initialInformation = {
  name: "",
  phone: "",
  email: "",
};

export default function BookingDetailsModal({ show, hideFunction, booking }) {
  const [userInformation, setUserInformation] = useState(initialInformation);

  useEffect(() => {
    if (show) getInformation();
  }, [show]);

  const getInformation = () => {
    Axios.get(`/api/users/${booking.booker}`)
      .then((response) => {
        setUserInformation(response.data);
      })
      .catch((response) => {
        console.log(response.response);
        toaster.error(
          `Fel när kontaktuppgifter skulle hämtas: ${response.response.data.message}`
        );
      });
  };

  return (
    <Modal show={show} onHide={hideFunction}>
      <Modal.Header>
        <Modal.Title>Kontaktuppgifter</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form>
          <Form.Text className="text-muted" />
          <Form.Group controlId="name">
            <Form.Label>Namn</Form.Label>
            <Form.Control
              type="text"
              onChange={(e) =>
                setUserInformation({ ...userInformation, name: e.target.value })
              }
              value={userInformation.name}
              disabled
            />
          </Form.Group>
          <Form.Group controlId="phone">
            <Form.Label>Telefon</Form.Label>
            <Form.Control
              type="text"
              onChange={(e) =>
                setUserInformation({
                  ...userInformation,
                  phone: e.target.value,
                })
              }
              value={userInformation.phone}
              disabled
            />
          </Form.Group>
          <Form.Group controlId="email">
            <Form.Label>E-post</Form.Label>
            <Form.Control
              type="email"
              onChange={(e) =>
                setUserInformation({
                  ...userInformation,
                  email: e.target.value,
                })
              }
              value={userInformation.email}
              disabled
            />
          </Form.Group>
        </Form>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={hideFunction}>
          Stäng
        </Button>
      </Modal.Footer>
    </Modal>
  );
}
