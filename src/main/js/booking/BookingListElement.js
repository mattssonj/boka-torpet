import React from 'react';

export default function BookingListRow(props) {

    return (
        <div>
            <h1>Bokning {props.booking.booker}</h1>
            <p>{props.booking.startDate} till {props.booking.endDate}</p>
        </div>
    )
};

