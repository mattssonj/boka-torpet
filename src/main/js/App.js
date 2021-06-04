import React, { Component } from "react";
import ReactDOM from "react-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter, Route, Switch } from "react-router-dom";
import { ToastContainer } from "react-toastify";
import Home from "./Home";
import Admin from "./admin/Admin";

import "react-toastify/dist/ReactToastify.css";

class App extends Component {
  render() {
    return (
      <div>
        <ToastContainer autoClose={false} newestOnTop />
        <Switch>
          <Route path="/admin">
            <Admin />
          </Route>

          <Route path="/">
            <Home />
          </Route>
        </Switch>
      </div>
    );
  }
}

ReactDOM.render(
  <BrowserRouter>
    <App />
  </BrowserRouter>,
  document.getElementById("react")
);
