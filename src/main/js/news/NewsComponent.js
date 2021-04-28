import React, {useEffect, useState} from 'react';
import Axios from "axios";

import generic_styles from "../css/Generics.module.css"
import {Col} from "react-bootstrap";

const ErrorMessage = 'Kunde inte hämta nyheter.'

export default function News() {

    const [news, setNews] = useState('')

    useEffect(() => {
        Axios.get('/api/messages/newest').then(response => {
            setNews(response.data.message)
        }).catch(error => {
            setNews(ErrorMessage + ' ' + error)
        });
    });

    return (
        <Col>
            <h1 className={generic_styles.section}>Senaste nyheterna</h1>
            <div>
                <p className={generic_styles.message}>{news}</p>
            </div>
        </Col>
    );
}