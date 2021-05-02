import React from 'react';

import {Col, Container, Row} from "react-bootstrap";
import Logout from "../Logout";
import AdminJumbotron from "./AdminJumbotron";
import AddUserComponent from "./AddUserComponent";

export default function Admin() {

    return (
        <Container>
            <Row><AdminJumbotron/></Row>
            <Row><AddUserComponent /></Row>
            <Row></Row>
            <Row><Col><Logout/></Col></Row>
        </Container>
    );
}