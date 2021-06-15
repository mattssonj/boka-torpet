import React from "react";
import ReactDOM from "react-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter, Route, Switch } from "react-router-dom";
import { ToastContainer } from "react-toastify";
import Home from "./Home.jsx";
import Admin from "./admin/Admin.jsx";
import "react-toastify/dist/ReactToastify.css";
import { Col, Container, Row } from "react-bootstrap";
import SidebarComponent from "./sidebar/SidebarComponent.jsx";
import HomeComponent from "./home/HomeComponent";
import MyUserInformationComponent from "./my_user/MyUserInformationComponent";
import MessagesComponent from "./messages/MessagesComponent";

const App = () => (
  <Container>
    <Row>
      <ToastContainer autoClose={false} newestOnTop />
      <Col md={2} className="sticky-top">
        <SidebarComponent />
      </Col>
      <Col xs={12} sm={12} md={10}>
        <Switch>
          <Route path="/path/admin" component={Admin} />
          <Route path="/path/bookings" component={Home} />
          <Route
            path="/path/my_user_information"
            component={MyUserInformationComponent}
          />
          <Route path="/path/messages" component={MessagesComponent} />

          <Route path="/path/home" component={HomeComponent} />
          <Route path="/" component={HomeComponent} />
        </Switch>
      </Col>
    </Row>
  </Container>
);

ReactDOM.render(
  <BrowserRouter>
    <App />
  </BrowserRouter>,
  document.getElementById("react")
);
