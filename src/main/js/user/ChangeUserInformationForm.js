import React, { useEffect, useState } from "react";
import Axios from "axios";

import { Button, Form, Modal } from "react-bootstrap";
import { toaster } from "../common/Toaster";

const initialFormObject = {
  name: "",
  phone: "",
  email: "",
};

export default function ChangeUserInformationForm({ show, hideFunction }) {
  const [isSaving, setIsSaving] = useState(false);
  const [userInformation, setUserInformation] = useState(initialFormObject);

  useEffect(() => {
    if (show) getInformation();
  }, [show]);

  const saveInformation = async () => {
    setIsSaving(true);
    Axios.put("/api/users/current", userInformation)
      .then((response) => {
        setIsSaving(false);
        setUserInformation(response.data);
        toaster.success("Användaruppgifter sparade");
        hideFunction();
      })
      .catch((error) => {
        console.log(error.response);
        setIsSaving(false);
      });
  };

  const getInformation = () => {
    Axios.get("/api/users/current")
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
        <Modal.Title>Mina kontaktuppgifter</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form>
          <Form.Text className="text-muted">
            Denna informationen kan ses när du skapat en bokning för att
            personer som undrar något ska kunna kontakta dig.
          </Form.Text>
          <Form.Group controlId="name">
            <Form.Label>Namn</Form.Label>
            <Form.Control
              type="text"
              onChange={(e) =>
                setUserInformation({ ...userInformation, name: e.target.value })
              }
              value={userInformation.name}
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
          onClick={!isSaving ? saveInformation : null}
          disabled={isSaving}
        >
          {isSaving ? "Sparar..." : "Spara"}
        </Button>
      </Modal.Footer>
    </Modal>
  );
}
