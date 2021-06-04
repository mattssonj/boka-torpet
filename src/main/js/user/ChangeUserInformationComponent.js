import React, { useState } from "react";

import { Button, Col } from "react-bootstrap";
import ChangeUserInformationForm from "./ChangeUserInformationForm";

export default function ChangeUserInformationComponent() {
  const [showModal, setShowModal] = useState(false);

  const hideModal = () => setShowModal(false);

  return (
    <Col>
      <Button
        variant="outline-primary"
        size="sm"
        block
        onClick={() => setShowModal(true)}
      >
        Mina kontaktuppgifter
      </Button>
      <ChangeUserInformationForm show={showModal} hideFunction={hideModal} />
    </Col>
  );
}
