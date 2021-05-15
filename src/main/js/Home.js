import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

import Logout from './Logout.js';
import News from "./news/NewsComponent";
import Bookings from "./booking/BookingListComponent";
import {Col, Container, Row} from "react-bootstrap";
import CreateBooking from "./booking/CreatingBookingComponent";

export default function Home() {

    return (
        <Container>
            <Row><News/></Row>
            <Row><CreateBooking/></Row>
            <Row><Bookings/></Row>
            <Row><Col><Logout/></Col></Row>
        </Container>
    )

};