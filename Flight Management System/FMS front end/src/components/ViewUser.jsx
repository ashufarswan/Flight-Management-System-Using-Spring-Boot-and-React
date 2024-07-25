import React, { useContext, useEffect, useState } from "react";
import {
  Container,
  Row,
  Col,
  Navbar,
  Nav,
  NavItem,
  NavLink,
} from "react-bootstrap";
import "./../css/SideBar.css";
import FlightLayout from "./FlightComponents/FlightLayout";
import getAllFlights from "./FlightComponents/FlightAPIAccess";
import BookingLayout from "./BookingsComponents/BookingLayout";
import { ViewBookingContext } from "./../Context/ViewBookingContext";
import { FaEye } from "react-icons/fa";
import { IoIosAddCircle } from "react-icons/io";
import { CiLogout } from "react-icons/ci";
import PassengerForm from "./PassngersComponents/PassengerForm";
import FlightSeats from "./SeatSelectionComponents/FlightSeats";
import { useNavigate } from "react-router-dom";
import { jwtDecode } from "jwt-decode";

const ViewUser = () => {
  const [flights, setFlights] = useState([]);
  const navigate = useNavigate()
  
  const viewBookingContextObject = useContext(ViewBookingContext);
  const handleClick = (event) => {
    event.preventDefault();
    if (event.target.id === "viewflight") {
      viewBookingContextObject.setviewFlight(true);
      viewBookingContextObject.setviewBooking(false);
      viewBookingContextObject.setviewPassenger(false);
      viewBookingContextObject.setviewSeat(false);
    } else if (event.target.id === "addflight") {
      viewBookingContextObject.setviewFlight(false);
      viewBookingContextObject.setviewBooking(true);
      viewBookingContextObject.setviewPassenger(false);
      viewBookingContextObject.setviewSeat(false);
    }
  };
  useEffect(() => {
    console.log("Render on Mount");
    getAllFlights().then((data) => {
      setFlights(data);
    });
  }, []);

  // console.log(flights[0])
  return (
    <Container className="bg-white"
      fluid
      style={{ height: "87vh", position: "relative", top: "0px" }}
    >
      <Row>
        <Col xs={2} md={2} lg={2} className="bg-dark side h-100">
          <Navbar variant="dark" expand={false}>
            <Nav className="flex-column">
              <NavItem>
                <NavLink id="viewflight" onClick={handleClick}>
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    width="15"
                    height="15"
                    viewBox="0 0 24 24"
                    fill="none"
                    stroke="currentColor"
                    strokeWidth="2"
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    className="feather feather-plus-square"
                  >
                    <rect
                      x="3"
                      y="3"
                      width="18"
                      height="18"
                      rx="2"
                      ry="2"
                    ></rect>
                    <line x1="12" y1="8" x2="12" y2="16"></line>
                    <line x1="8" y1="12" x2="16" y2="12"></line>
                  </svg>
                  Book Flight
                </NavLink>
              </NavItem>
              <NavItem>
                <NavLink id="addflight" onClick={handleClick}>
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    width="15"
                    height="15"
                    viewBox="0 0 24 24"
                    fill="none"
                    stroke="currentColor"
                    strokeWidth="2"
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    className="feather feather-eye"
                  >
                    <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"></path>
                    <circle cx="12" cy="12" r="3"></circle>
                  </svg>
                  View Booking
                </NavLink>
              </NavItem>
              <NavItem>
                <NavLink href="/logout">
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    width="15"
                    height="15"
                    viewBox="0 0 24 24"
                    fill="none"
                    stroke="currentColor"
                    strokeWidth="2"
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    className="feather feather-log-out"
                  >
                    <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"></path>
                    <polyline points="16 17 21 12 16 7"></polyline>
                    <line x1="21" y1="12" x2="9" y2="12"></line>
                  </svg>
                  Logout
                </NavLink>
              </NavItem>
            </Nav>
          </Navbar>
        </Col>
        <Col
          xs={10}
          md={10}
          lg={10}
          className="main-content"
          style={{ "overflowY": "scroll"}}
        >
          {viewBookingContextObject.viewFlight && (
            <div>
              <FlightLayout />
            </div>
          )}
          {viewBookingContextObject.viewBooking && (
            <div>
              <BookingLayout />
            </div>
          )}
          {viewBookingContextObject.viewPassenger && (
            <div>
              <PassengerForm />
            </div>
          )}
          {viewBookingContextObject.viewSeat && (
            <div
              style={{
                position: "absolute",
                top: 0,
                right: 0,
                width: "80%",
                paddingTop: "20px",
                paddingRight: "30px",
              }}
            >
              <FlightSeats />
            </div>
          )}
        </Col>
      </Row>
    </Container>
  );
};

export default ViewUser;
