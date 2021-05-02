import React, {Component} from 'react';
import ReactDOM from 'react-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import {BrowserRouter, Switch, Route} from "react-router-dom";
import Boka from "./boka/Boka";
import Admin from "./admin/Admin";

class App extends Component {

    render() {
        return (
            <Switch>
                <Route path="/admin">
                    <Admin/>
                </Route>
                <Route path="/">
                    <Boka/>
                </Route>
            </Switch>
        )
    }

}

ReactDOM.render(
    <BrowserRouter>
        <App/>
    </BrowserRouter>,
    document.getElementById('react')
);