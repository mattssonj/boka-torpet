import React, {Component} from 'react';
import ReactDOM from 'react-dom';
import 'bootstrap/dist/css/bootstrap.min.css';

import Logout from './Logout.js';
import News from "./news/NewsComponent";
import Bookings from "./booking/BookingListComponent";

class App extends Component {

    render() {
        return (
            <div>
                <h1>Hello World</h1>
                <News />
                <Bookings />
                <Logout />
            </div>
        )
    }

}

ReactDOM.render(
    <App />,
    document.getElementById('react')
);