import React, {useEffect, useState} from 'react';
import Axios from "axios";

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
        <div>
            <h1>Senaste nyheterna</h1>
            <p>{news}</p>
        </div>
    );
}