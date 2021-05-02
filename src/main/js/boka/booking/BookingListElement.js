import React from 'react';
import {Card} from "react-bootstrap";

export default function BookingListRow(props) {

    return (
        <Card border="success">
            <Card.Header className="text-center" as="h5">{props.booking.name}</Card.Header>
            <Card.Body>
                <Card.Text>{props.booking.message}</Card.Text>
                <Card.Text className="text-right">Bokning <b>{props.booking.booker}</b></Card.Text>
            </Card.Body>
            <Card.Footer className="text-center text-muted">{props.booking.startDate} till {props.booking.endDate}</Card.Footer>
        </Card>
    )
}
