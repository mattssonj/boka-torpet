import React from 'react';
import {Card, Col, Row} from "react-bootstrap";
import BookingDetails from "./BookingDetails";

export default function BookingListRow(props) {
    return (
        <Card border="success">
            <Card.Header className="text-center" as="h5">{props.booking.name}</Card.Header>
            <Card.Body>
                <Row><Col><Card.Text>{props.booking.message}</Card.Text></Col></Row>
                <br/>
                <Row className="align-items-center">
                    <Col><Card.Text>Bokning <b>{props.booking.booker}</b></Card.Text></Col>
                    <Col><BookingDetails booking={props.booking}/></Col>
                </Row>
            </Card.Body>
            <Card.Footer
                className="text-center text-muted">{props.booking.startDate} till {props.booking.endDate}</Card.Footer>
        </Card>
    )
}
