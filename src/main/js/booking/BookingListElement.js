import React from 'react';
import {Card, Col, Row} from "react-bootstrap";
import BookingDetails from "./BookingDetails";
import EditBookingButton from "./EditBookingButton";

export default function BookingListRow(props) {

    return (
        <Card border="success">
            <Card.Header className="text-center" as="h5">
                {props.booking.name}
            </Card.Header>
            <Card.Body>
                <Row><Col><Card.Text>{props.booking.message}</Card.Text></Col></Row>
                <br/>
                <Row className="align-items-center">
                    <Col><Card.Text>Bokare: <b>{props.booking.booker}</b></Card.Text></Col>
                    <Col>{props.booking.booker === props.username ? <EditBookingButton booking={props.booking}/> : null}</Col>
                    <Col><BookingDetails booking={props.booking}/></Col>
                </Row>
            </Card.Body>
            <Card.Footer
                className="text-center text-muted">{props.booking.startDate} till {props.booking.endDate}</Card.Footer>
        </Card>
    )
}
