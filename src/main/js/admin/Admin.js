import React from 'react';

import {Col, Container, Row} from "react-bootstrap";
import Logout from "../Logout";
import AdminJumbotron from "./AdminJumbotron";
import AddUserComponent from "./AddUserComponent";
import UserListComponent from "./UserListComponent";

export default function Admin() {

    return (
        <Container>
            <Row><AdminJumbotron/></Row>
            <Row><AddUserComponent /></Row>
            <br/>
            <Row><UserListComponent showAll={true} /></Row>
            <Row><Col><Logout/></Col></Row>
        </Container>
    );
}