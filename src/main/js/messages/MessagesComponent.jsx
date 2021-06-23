import { Button, Col, Container, Row } from "react-bootstrap";
import React, { useEffect, useRef, useState } from "react";
import WriteMessageComponent from "./WriteMessageComponent";
import messageClient from "../common/clients/MessageClient";
import Message from "./Message";

const pageSize = 10;
const initialPageInfo = {
  pageNumber: 0,
  isLast: false,
};

export default function MessagesComponent() {
  const input = useRef(null);
  const [pageInfo, setPageInfo] = useState(initialPageInfo);
  const [messages, setMessages] = useState([]);

  useEffect(() => {
    updateMessages();
  }, []);

  /*useEffect(() => {
        input.current.scrollIntoView({behavior: "smooth"});
    }, [messages])*/ // TODO not sure we want this... decide later on

  const updateMessages = () => {
    !pageInfo.isLast
      ? messageClient
          .getMessagePage(pageInfo.pageNumber, pageSize)
          .then((page) => {
            setPageInfo({
              pageNumber: pageInfo.pageNumber + 1,
              isLast: page.isLast,
            });
            setMessages([...messages, ...page.content]);
          })
          .catch((_) => {})
      : null; // Only if we are not on last page
  };

  const mapMessages = () =>
    [...messages].reverse().map((message) => {
      // reverse since it sorted newest first
      return (
        <Row key={message.id} className="m-1">
          <Message key={message.id} message={message} />
        </Row>
      );
    });

  const addToMessages = (msg) =>
    setMessages(formatToArray(msg).concat(messages));
  const formatToArray = (any) => (Array.isArray(any) ? any : [any]);

  return (
    <Container>
      <Row>
        <Col>
          <h1>Meddelanden</h1>
        </Col>
      </Row>
      <Row>
        <Col className="text-center">
          {pageInfo.isLast ? (
            <p>Det finns inga mer meddelanden</p>
          ) : (
            <Button variant="link" onClick={updateMessages}>
              Hämta mer
            </Button>
          )}
        </Col>
      </Row>
      <hr />
      <Row className="overflow-auto">
        <Col>{mapMessages()}</Col>
      </Row>
      <hr />
      <Row ref={input}>
        <WriteMessageComponent returnValueHandler={addToMessages} />
      </Row>
      <br />
    </Container>
  );
}
