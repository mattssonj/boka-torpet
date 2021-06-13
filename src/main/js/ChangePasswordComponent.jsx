import React, { useState } from "react";
import Button from "react-bootstrap/Button";
import { Col, Form, Modal } from "react-bootstrap";
import Axios from "axios";
import { toaster } from "./common/Toaster";

export default function ChangePasswordComponent({ username }) {
  const [showModal, setShowModal] = useState(false);

  const hideModal = () => setShowModal(false);

  const changePassword = async (newPassword) =>
    Axios.post("/api/admin/users/changePassword", {
      username,
      newPassword,
    })
      .then((resp) => console.log(resp))
      .catch((error) => {
        toaster.error(
          `Det gick inte att uppdatera lösenordet: ${error.response.data.message}`
        );
        throw error;
      });

  return (
    <Col>
      <Button
        variant="outline-primary"
        size="sm"
        block
        onClick={() => setShowModal(true)}
      >
        Byt lösenord
      </Button>
      <ChangePasswordForm
        show={showModal}
        hideFunction={hideModal}
        changePassword={changePassword}
      />
    </Col>
  );
}

const passwordsInitialState = {
  password: "",
  confPassword: "",
};

function ChangePasswordForm({ show, hideFunction, changePassword }) {
  const [isSaving, setIsSaving] = useState(false);
  const [passwords, setPasswords] = useState(passwordsInitialState);

  const updatePassword = async () => {
    setIsSaving(true);
    if (
      passwords.password.length < 6 ||
      passwords.password !== passwords.confPassword
    ) {
      toaster.warning(
        "Lösenord byttes inte. De är inte lika eller 6 tecken långt"
      );
    } else {
      await changePassword(passwords.password)
        .then((_) => {
          toaster.success("Lösenord uppdaterat");
          setPasswords(passwordsInitialState);
          hideFunction();
        })
        .catch((_) => {});
    }
    setIsSaving(false);
  };

  return (
    <Modal show={show} onHide={hideFunction}>
      <Modal.Header>
        <Modal.Title>Byt lösenord</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form>
          <Form.Text className="text-muted">
            Lösenord får inte vara tomt och måste vara minst 6 tecken
          </Form.Text>
          <Form.Group controlId="password">
            <Form.Label>Lösenord</Form.Label>
            <Form.Control
              type="password"
              onChange={(e) =>
                setPasswords({ ...passwords, password: e.target.value })
              }
              value={passwords.password}
            />
          </Form.Group>
          <Form.Group controlId="confPassword">
            <Form.Label>Bekräfta lösenord</Form.Label>
            <Form.Control
              type="password"
              onChange={(e) =>
                setPasswords({
                  ...passwords,
                  confPassword: e.target.value,
                })
              }
              value={passwords.confPassword}
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
          onClick={!isSaving ? updatePassword : null}
          disabled={isSaving}
        >
          {isSaving ? "Uppdaterar..." : "Uppdatera"}
        </Button>
      </Modal.Footer>
    </Modal>
  );
}
