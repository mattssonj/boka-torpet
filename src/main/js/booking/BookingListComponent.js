import React, {useEffect, useState} from 'react';
import Axios from "axios";

const ErrorMessage = 'Kunde inte hämta bokningar.'
const EmptyMessage = 'Inga bokningar tillgängliga'

export default function Bookings() {

    const [bookings, setBookings] = useState([])

    useEffect(() => {
        Axios.get('/api/bookings').then(response => {
            setBookings(response.data)
        }).catch(error => {
            setBookings([])
            console.log('Unable to receive bookings. Error {}', error)
        });
    });

    return (
        <div>
            <h1>Bokningar</h1>
            {bookings.map(booking => (
                <div key={booking.id}>
                    <p>Startar: {booking.startDate}</p>
                    <p>Slutar: {booking.endDate}</p>
                </div>
            ))}
        </div>
    );
}