import React, { useState } from "react";
import Axios from "axios";

import { Button, Col, Container, Form, Modal, Row } from "react-bootstrap";

const initialFormObject = {
  message: "",
};

export default function WriteMessageComponent({ returnValueHandler }) {
  const [isCreating, setIsCreating] = useState(false);
  const [formValues, setFormValues] = useState(initialFormObject);

  const resetForm = () => setFormValues(initialFormObject);

  const createMessage = async () => {
    setIsCreating(true);
    postRequest()
      .then((response) => {
        setIsCreating(false);
        resetForm();
        returnValueHandler ? returnValueHandler(response.data) : null;
      })
      .catch((error) => {
        console.log(error.response);
        setIsCreating(false);
        // TODO add some error messaging here
      });
  };

  const postRequest = async () => Axios.post("/api/messages", formValues);

  return (
    <Container>
      <Row>
        <Col>
          <Form>
            <Form.Group controlId="message">
              <Form.Label>Nytt Meddelande</Form.Label>
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
        </Col>
      </Row>
      <Row>
        <Col>
          <Button variant="secondary" onClick={resetForm}>
            Rensa
          </Button>
        </Col>
        <Col className="text-right">
          <Button
            variant="success"
            onClick={!isCreating ? createMessage : null}
            disabled={isCreating}
          >
            {isCreating ? "Skriver..." : "Skriv"}
          </Button>
        </Col>
      </Row>
    </Container>
  );
}
