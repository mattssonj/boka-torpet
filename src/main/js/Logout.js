import React from 'react';
import Axios from 'axios';
import Button from 'react-bootstrap/Button';

export default function Logout() {
    return (
        <div>
            <br />
            <Button variant="info" onClick={() => {
                console.log('Starting logout process...')
                Axios.post('/logout').then(() => {
                    console.log('Logout process complete')
                    location.reload()
                });
            }}>
                Logout
            </Button>
        </div>
    );
}