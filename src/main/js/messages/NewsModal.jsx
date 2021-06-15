import React, { useState } from "react";
import Axios from "axios";

import { Button, Form, Modal } from "react-bootstrap";

const initialFormObject = {
  message: "",
};

export default function NewsModal({ show, hideFunction, newMessage }) {
  const [isCreating, setIsCreating] = useState(false);
  const [formValues, setFormValues] = useState(initialFormObject);

  const resetForm = () => setFormValues(initialFormObject);

  const createNews = async () => {
    setIsCreating(true);
    postRequest()
      .then((response) => {
        setIsCreating(false);
        resetForm();
        newMessage(response.data);
      })
      .catch((error) => {
        console.log(error.response);
        setIsCreating(false);
        // TODO add some error messaging here
      });
  };

  const postRequest = async () => Axios.post("/api/messages", formValues);

  return (
    <Modal show={show} onHide={hideFunction}>
      <Modal.Header>
        <Modal.Title>Skriv nytt meddelande</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form>
          <Form.Group controlId="bookingName">
            <Form.Label>Meddelande</Form.Label>
            <Form.Control
              type="text"
              as="textarea"
              onChange={(e) =>
                setFormValues({ ...formValues, message: e.target.value })
              }
              value={formValues.message}
            />
          </Form.Group>
        </Form>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={hideFunction}>
          Stäng
        </Button>
        <Button
          variant="primary"
          onClick={!isCreating ? createNews : null}
          disabled={isCreating}
        >
          {isCreating ? "Uppdaterar..." : "Uppdatera"}
        </Button>
      </Modal.Footer>
    </Modal>
  );
}
