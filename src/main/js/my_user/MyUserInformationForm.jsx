import React, { useEffect, useState } from "react";
import Axios from "axios";
import toaster from "../common/Toaster";
import backendClient from "../common/BackendClient";
import { Button, Col, Container, Form, Row } from "react-bootstrap";

const initialFormObject = {
  name: "",
  phone: "",
  email: "",
};

export default function MyUserInformationForm() {
  const [isSaving, setIsSaving] = useState(false);
  const [userInformation, setUserInformation] = useState(initialFormObject);

  useEffect(() => {
    getInformation();
  }, []);

  const saveInformation = async () => {
    setIsSaving(true);
    Axios.put("/api/users/current", userInformation)
      .then((response) => {
        setIsSaving(false);
        setUserInformation(response.data);
        toaster.success("Användaruppgifter sparade");
      })
      .catch((error) => {
        console.log(error.response);
        setIsSaving(false);
      });
  };

  const getInformation = () => {
    backendClient
      .getCurrentLoggedInUser()
      .then((userInformation) => setUserInformation(userInformation))
      .catch((error) => {
        toaster.error(
          `Fel när kontaktuppgifter skulle hämtas: ${error.response.data.message}`
        );
      });
  };

  return (
    <Container>
      <Row>
        <Col>
          <h1>Mina Kontaktuppgifter</h1>
        </Col>
      </Row>
      <Row>
        <Col>
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
                  setUserInformation({
                    ...userInformation,
                    name: e.target.value,
                  })
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
          <Button
            variant="primary"
            onClick={!isSaving ? saveInformation : null}
            disabled={isSaving}
          >
            {isSaving ? "Sparar..." : "Spara"}
          </Button>
        </Col>
      </Row>
    </Container>
  );
}
