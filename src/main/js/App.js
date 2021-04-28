import React, {Component} from 'react';
import ReactDOM from 'react-dom';
import 'bootstrap/dist/css/bootstrap.min.css';

import Logout from './Logout.js';
import News from "./news/NewsComponent";
import Bookings from "./booking/BookingListComponent";
import {Col, Container, Jumbotron, Row} from "react-bootstrap";
import CreateBooking from "./booking/CreatingBookingComponent";

class App extends Component {

    render() {
        return (
            <Container>
                <Row><News/></Row>
                <Row><CreateBooking/></Row>
                <Row><Bookings/></Row>
                <Row><Col><Logout/></Col></Row>
            </Container>
        )
    }

}

ReactDOM.render(
    <App/>,
    document.getElementById('react')
);