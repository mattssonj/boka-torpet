import React, {useEffect, useState} from 'react';
import Axios from "axios";
import {Button, Col, Jumbotron, Row} from "react-bootstrap";

import NewsModal from "./NewsModal";
import DateFormatter from "../common/DateFormatter";

const ErrorMessage = 'Kunde inte hämta nyheter.'

const InitialNews = {
    message: '',
    written: '',
    writer: ''
}

export default function News() {

    const [news, setNews] = useState(InitialNews)
    const [showModal, setShowModal] = useState(false)

    const updateNews = (data) => {
        setNews({message: data.message, writer: data.writer, written: DateFormatter(data.createdTimestamp)})
    }

    const modalCreation = (data) => {
        setShowModal(false)
        updateNews(data)
    }

    useEffect(() => {
        Axios.get('/api/messages/newest').then(response => {
            updateNews(response.data)
        }).catch(error => {
            setNews({...news, message: `${ErrorMessage} ${error}`})
        });
    }, []);

    return (
        <Col>
            <Jumbotron>
                <Row><h1>Boka torpet</h1></Row>
                <Row><p>{news.message}</p></Row>
                <Row><small>Skrivet {news.written} av {news.writer}</small></Row>
                <Row><Button size="sm" variant="info" onClick={() => setShowModal(true)}>Nytt meddelande</Button></Row>
                <NewsModal show={showModal} hideFunction={() => setShowModal(false)} newMessage={modalCreation}/>
            </Jumbotron>
        </Col>
    );
}