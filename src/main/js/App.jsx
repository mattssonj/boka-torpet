import React from "react";
import ReactDOM from "react-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter, Route, Switch } from "react-router-dom";
import { ToastContainer } from "react-toastify";
import AdminComponent from "./admin/AdminComponent.jsx";
import "react-toastify/dist/ReactToastify.css";
import { Col, Container, Row } from "react-bootstrap";
import SidebarComponent from "./sidebar/SidebarComponent.jsx";
import HomeComponent from "./home/HomeComponent";
import MyUserInformationComponent from "./my_user/MyUserInformationComponent";
import MessagesComponent from "./messages/MessagesComponent";
import BookingComponent from "./booking/BookingComponent";

const App = () => (
  <Container fluid>
    <Row>
      <ToastContainer autoClose={false} newestOnTop />
      <Col md={3} lg={2} className="sticky-top">
        <br />
        <SidebarComponent />
      </Col>
      <Col xs={12} sm={12} md={9} lg={10}>
        <br />
        <Switch>
          <Route path="/path/admin" component={AdminComponent} />
          <Route path="/path/bookings" component={BookingComponent} />
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
