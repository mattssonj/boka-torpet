import React, {useEffect, useState} from 'react';
import Axios from "axios";

import BookingListRow from "./BookingListElement";
import {Col, Row} from "react-bootstrap";
import {toaster} from "../common/Toaster";

export default function Bookings() {

    const [bookings, setBookings] = useState([])
    const [loggedInUsername, setLoggedInUsername] = useState('')

    useEffect(() => {
        Axios.get('/api/bookings').then(response => {
            setBookings(response.data)
        }).catch(error => {
            setBookings([])
            console.log('Unable to receive bookings. Error {}', error)
        });

        Axios.get('/api/users/current')
            .then(response => {
                setLoggedInUsername(response.data.username)
            }).catch(response => {
            console.log(response.response)
        })
    }, []); // The empty array here makes the hook only trigger once

    return (
        <Col>
            <br />
            {bookings.map(booking => (
                <div key={booking.id}>
                    <BookingListRow booking={booking} username={loggedInUsername}/>
                    <br />
                </div>
            ))}
        </Col>
    );
}