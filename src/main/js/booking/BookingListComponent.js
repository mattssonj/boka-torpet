import React, {useEffect, useState} from 'react';
import Axios from "axios";

import BookingListRow from "./BookingListElement";
import {Button, Col, Row} from "react-bootstrap";

export default function Bookings() {

    const [bookings, setBookings] = useState([])
    const [showing, setShowing] = useState([])
    const [showAll, setShowAll] = useState(true)
    const [loggedInUsername, setLoggedInUsername] = useState('')

    useEffect(() => {
        getBookings(response => {
            setBookings(response)
            setShowing(response)
        })
        getLoggedInUsername()
    }, []); // The empty array here makes the hook only trigger once


    const updateBookings = () => {
        getBookings(response => {
            setBookings(response)
            showAll ? showAllBookings() : showLoggedInBookings()
        })
    };

    const getBookings = (responseHandler) => {
        Axios.get('/api/bookings').then(response => {
            responseHandler(response.data)
        }).catch(error => {
            console.log('Unable to receive bookings. Error {}', error)
            responseHandler([])
        });
    }

    const showAllBookings = async () => {
        setShowAll(true)
        setShowing(bookings)
    };

    const showLoggedInBookings = async () => {
        setShowAll(false)
        setShowing(bookings.filter(booking => booking.booker === loggedInUsername))
    }

    const getLoggedInUsername = () => {
        Axios.get('/api/users/current')
            .then(response => {
                setLoggedInUsername(response.data.username)
            }).catch(response => {
            console.log(response.response)
        })
    }

    return (
        <Col>
            <br/>
            <Row className="justify-content-md-center">
                <Col md="auto"><Button variant="outline-secondary" onClick={updateBookings}>Uppdatera listan</Button></Col>
                <Col md="auto"><Button variant={!showAll ? "secondary" : "outline-secondary"} onClick={showLoggedInBookings}>Visa
                    mina</Button></Col>
                <Col md="auto"><Button variant={showAll ? "secondary" : "outline-secondary"} onClick={showAllBookings}>Visa
                    alla</Button></Col>
            </Row>
            <Row>
                <Col>
                    <br/>
                    {showing.map(booking => (
                        <div key={booking.id}>
                            <BookingListRow booking={booking} username={loggedInUsername}/>
                            <br/>
                        </div>
                    ))}
                </Col>
            </Row>
        </Col>
    );
}