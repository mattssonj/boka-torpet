import React, { useEffect, useState } from "react";
import { Link, useHistory } from "react-router-dom";
import Axios from "axios";
import { Button, Col, Jumbotron, Row } from "react-bootstrap";

import NewsModal from "./NewsModal";
import DateFormatter from "../common/DateFormatter";
import ChangeUserInformationComponent from "../user/ChangeUserInformationComponent";
import ChangePasswordComponent from "../ChangePasswordComponent";
import { backendClient } from "../common/BackendClient";

const ErrorMessage = "Kunde inte hämta nyheter.";

const InitialNews = {
  message: "",
  written: "",
  writer: "",
};

export default function News() {
  const [news, setNews] = useState(InitialNews);
  const [showModal, setShowModal] = useState(false);
  const [loggedInUsername, setLoggedInUsername] = useState("");

  const updateNews = (data) => {
    setNews({
      message: data.message,
      writer: data.writer,
      written: DateFormatter(data.createdAt),
    });
  };

  const modalCreation = (data) => {
    setShowModal(false);
    updateNews(data);
  };

  useEffect(() => {
    Axios.get("/api/messages/newest")
      .then((response) => {
        updateNews(response.data);
      })
      .catch((error) => {
        setNews({ ...news, message: `${ErrorMessage} ${error}` });
      });

    backendClient
      .getCurrentLoggedInUser()
      .then((user) => setLoggedInUsername(user.username));
  }, []);

  return (
    <Col>
      <Jumbotron>
        <Row>
          <Col>
            <h1>Boka torpet</h1>
          </Col>
        </Row>
        <Row>
          <Col>
            <p>{news.message}</p>
          </Col>
        </Row>
        <Row>
          <Col>
            <small>
              Skrivet {news.written} av {news.writer}
            </small>
          </Col>
        </Row>
        <Row>
          <Col>
            <Button size="sm" variant="info" onClick={() => setShowModal(true)}>
              Nytt meddelande
            </Button>
          </Col>
          <Col className="text-center">
            <ChangeUserInformationComponent />
            <ChangePasswordComponent username={loggedInUsername} />
          </Col>
          <Col className="text-right">
            <Link
              className="btn btn-outline-dark btn-sm"
              role="button"
              to="/admin"
            >
              Admin
            </Link>
          </Col>
        </Row>
        <NewsModal
          show={showModal}
          hideFunction={() => setShowModal(false)}
          newMessage={modalCreation}
        />
      </Jumbotron>
    </Col>
  );
}
