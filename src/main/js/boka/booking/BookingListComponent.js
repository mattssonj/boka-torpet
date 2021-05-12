import React, {useEffect, useState} from 'react';
import Axios from "axios";

import BookingListRow from "./BookingListElement";
import {Col, Row} from "react-bootstrap";

const ErrorMessage = 'Kunde inte hämta bokningar.'
const EmptyMessage = 'Inga bokningar tillgängliga'

export default function Bookings() {

    const [bookings, setBookings] = useState([])

    useEffect(() => {
        Axios.get('/api/bookings').then(response => {
            setBookings(response.data)
        }).catch(error => {
            setBookings([])
            console.log('Unable to receive bookings. Error {}', error)
        });
    }, []); // The empty array here makes the hook only trigger once

    return (
        <Col>
            <br />
            {bookings.map(booking => (
                <div key={booking.id}>
                    <BookingListRow booking={booking}/>
                    <br />
                </div>
            ))}
        </Col>
    );
}