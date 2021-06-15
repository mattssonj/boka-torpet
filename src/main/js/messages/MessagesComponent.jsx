import { Button, Col, Container, Row } from "react-bootstrap";
import React, { useEffect, useRef } from "react";
import { exampleMessage } from "../common/ExampleData";
import Message from "./Message";
import WriteMessageComponent from "./WriteMessageComponent";

let i = 0;
const exampleMessages = [...Array(10).keys()]
  .map((_) => exampleMessage)
  .map((msg) => (
    <Row className="m-1">
      <Message key={i++} message={msg} />
    </Row>
  ));

export default function MessagesComponent() {
  const input = useRef(null);

  useEffect(() => {
    input.current.scrollIntoView({ behavior: "smooth" });
  });

  return (
    <Container>
      <Row>
        <Col>
          <h1>Meddelanden</h1>
        </Col>
      </Row>
      <Row>
        <Col className="text-center">
          <Button variant="link">Hämta mer</Button>
        </Col>
      </Row>
      <hr />
      <Row className="overflow-auto">
        <Col>
          {exampleMessages}
          {/*<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In vitae tempor ipsum. Proin at eros at turpis euismod
                        bibendum dignissim a metus. Morbi id enim vitae diam ornare condimentum vitae at urna. Donec scelerisque dui quis
                        massa rutrum consectetur. Lorem ipsum dolor sit amet, consectetur adipiscing elit. In vitae tempor ipsum. Proin at
                        eros at turpis euismod bibendum dignissim a metus. Morbi id enim vitae diam ornare condimentum vitae at urna. Donec
                        scelerisque dui quis massa rutrum consectetur.</p>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In vitae tempor ipsum. Proin at eros at turpis euismod
                        bibendum dignissim a metus. Morbi id enim vitae diam ornare condimentum vitae at urna. Donec scelerisque dui quis
                        massa rutrum consectetur. Lorem ipsum dolor sit amet, consectetur adipiscing elit. In vitae tempor ipsum. Proin at
                        eros at turpis euismod bibendum dignissim a metus. Morbi id enim vitae diam ornare condimentum vitae at urna. Donec
                        scelerisque dui quis massa rutrum consectetur.</p>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In vitae tempor ipsum. Proin at eros at turpis euismod
                        bibendum dignissim a metus. Morbi id enim vitae diam ornare condimentum vitae at urna. Donec scelerisque dui quis
                        massa rutrum consectetur. Lorem ipsum dolor sit amet, consectetur adipiscing elit. In vitae tempor ipsum. Proin at
                        eros at turpis euismod bibendum dignissim a metus. Morbi id enim vitae diam ornare condimentum vitae at urna. Donec
                        scelerisque dui quis massa rutrum consectetur.</p>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In vitae tempor ipsum. Proin at eros at turpis euismod
                        bibendum dignissim a metus. Morbi id enim vitae diam ornare condimentum vitae at urna. Donec scelerisque dui quis
                        massa rutrum consectetur. Lorem ipsum dolor sit amet, consectetur adipiscing elit. In vitae tempor ipsum. Proin at
                        eros at turpis euismod bibendum dignissim a metus. Morbi id enim vitae diam ornare condimentum vitae at urna. Donec
                        scelerisque dui quis massa rutrum consectetur.</p>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In vitae tempor ipsum. Proin at eros at turpis euismod
                        bibendum dignissim a metus. Morbi id enim vitae diam ornare condimentum vitae at urna. Donec scelerisque dui quis
                        massa rutrum consectetur. Lorem ipsum dolor sit amet, consectetur adipiscing elit. In vitae tempor ipsum. Proin at
                        eros at turpis euismod bibendum dignissim a metus. Morbi id enim vitae diam ornare condimentum vitae at urna. Donec
                        scelerisque dui quis massa rutrum consectetur.</p>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In vitae tempor ipsum. Proin at eros at turpis euismod
                        bibendum dignissim a metus. Morbi id enim vitae diam ornare condimentum vitae at urna. Donec scelerisque dui quis
                        massa rutrum consectetur. Lorem ipsum dolor sit amet, consectetur adipiscing elit. In vitae tempor ipsum. Proin at
                        eros at turpis euismod bibendum dignissim a metus. Morbi id enim vitae diam ornare condimentum vitae at urna. Donec
                        scelerisque dui quis massa rutrum consectetur.</p>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In vitae tempor ipsum. Proin at eros at turpis euismod
                        bibendum dignissim a metus. Morbi id enim vitae diam ornare condimentum vitae at urna. Donec scelerisque dui quis
                        massa rutrum consectetur. Lorem ipsum dolor sit amet, consectetur adipiscing elit. In vitae tempor ipsum. Proin at
                        eros at turpis euismod bibendum dignissim a metus. Morbi id enim vitae diam ornare condimentum vitae at urna. Donec
                        scelerisque dui quis massa rutrum consectetur.</p>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In vitae tempor ipsum. Proin at eros at turpis euismod
                        bibendum dignissim a metus. Morbi id enim vitae diam ornare condimentum vitae at urna. Donec scelerisque dui quis
                        massa rutrum consectetur. Lorem ipsum dolor sit amet, consectetur adipiscing elit. In vitae tempor ipsum. Proin at
                        eros at turpis euismod bibendum dignissim a metus. Morbi id enim vitae diam ornare condimentum vitae at urna. Donec
                        scelerisque dui quis massa rutrum consectetur.</p>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In vitae tempor ipsum. Proin at eros at turpis euismod
                        bibendum dignissim a metus. Morbi id enim vitae diam ornare condimentum vitae at urna. Donec scelerisque dui quis
                        massa rutrum consectetur. Lorem ipsum dolor sit amet, consectetur adipiscing elit. In vitae tempor ipsum. Proin at
                        eros at turpis euismod bibendum dignissim a metus. Morbi id enim vitae diam ornare condimentum vitae at urna. Donec
                        scelerisque dui quis massa rutrum consectetur.</p>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In vitae tempor ipsum. Proin at eros at turpis euismod
                        bibendum dignissim a metus. Morbi id enim vitae diam ornare condimentum vitae at urna. Donec scelerisque dui quis
                        massa rutrum consectetur. Lorem ipsum dolor sit amet, consectetur adipiscing elit. In vitae tempor ipsum. Proin at
                        eros at turpis euismod bibendum dignissim a metus. Morbi id enim vitae diam ornare condimentum vitae at urna. Donec
                        scelerisque dui quis massa rutrum consectetur.</p>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In vitae tempor ipsum. Proin at eros at turpis euismod
                        bibendum dignissim a metus. Morbi id enim vitae diam ornare condimentum vitae at urna. Donec scelerisque dui quis
                        massa rutrum consectetur. Lorem ipsum dolor sit amet, consectetur adipiscing elit. In vitae tempor ipsum. Proin at
                        eros at turpis euismod bibendum dignissim a metus. Morbi id enim vitae diam ornare condimentum vitae at urna. Donec
                        scelerisque dui quis massa rutrum consectetur.</p>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In vitae tempor ipsum. Proin at eros at turpis euismod
                        bibendum dignissim a metus. Morbi id enim vitae diam ornare condimentum vitae at urna. Donec scelerisque dui quis
                        massa rutrum consectetur. Lorem ipsum dolor sit amet, consectetur adipiscing elit. In vitae tempor ipsum. Proin at
                        eros at turpis euismod bibendum dignissim a metus. Morbi id enim vitae diam ornare condimentum vitae at urna. Donec
                        scelerisque dui quis massa rutrum consectetur.</p>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In vitae tempor ipsum. Proin at eros at turpis euismod
                        bibendum dignissim a metus. Morbi id enim vitae diam ornare condimentum vitae at urna. Donec scelerisque dui quis
                        massa rutrum consectetur. Lorem ipsum dolor sit amet, consectetur adipiscing elit. In vitae tempor ipsum. Proin at
                        eros at turpis euismod bibendum dignissim a metus. Morbi id enim vitae diam ornare condimentum vitae at urna. Donec
                        scelerisque dui quis massa rutrum consectetur.</p>*/}
        </Col>
      </Row>
      <hr />
      <Row ref={input}>
        <WriteMessageComponent />
      </Row>
      <br />
    </Container>
  );
}
