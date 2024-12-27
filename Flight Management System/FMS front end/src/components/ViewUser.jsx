import React, { useContext, useEffect } from "react";
import {
  Container,
  Row,
  Col,
  Navbar,
  Nav,
  NavItem,
  NavLink,
} from "react-bootstrap";
import FlightLayout from "./FlightComponents/FlightLayout";
import getAllFlights from "./FlightComponents/FlightAPIAccess";
import BookingLayout from "./BookingsComponents/BookingLayout";
import { ViewBookingContext } from "./../Context/ViewBookingContext";

import { BookingContextProvider } from "../Context/BookingContext";
 

const ViewUser = () => {

  const viewBookingContextObject = useContext(ViewBookingContext);
  const handleClick = (event) => {
    event.preventDefault();
    if (event.target.id === "viewflight") {
      viewBookingContextObject.setviewFlight(true);
      viewBookingContextObject.setviewBooking(false);
    } else if (event.target.id === "addflight") {
      viewBookingContextObject.setviewFlight(false);
      viewBookingContextObject.setviewBooking(true);
    }
  };
  useEffect(() => {
    //console.log("Render on Mount");
    getAllFlights().then((data) => {
      // setFlights(data);
    });
  }, []);

  // //console.log(flights[0])
  return (
    <Container className="bg-white"
      fluid
      style={{  position: "relative", top: "0px" }}
    >
      <Row >
        <Col lg={12} >
          <Navbar >
            <Nav >
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
                  &nbsp; Book Flight  &nbsp;&nbsp;
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
                  &nbsp; View Booking &nbsp;&nbsp;
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
                  &nbsp; Logout
                </NavLink>
              </NavItem>
            </Nav>
          </Navbar>
        </Col>
        <Col
         
          lg={12}
          style={{ "overflowY": "scroll"}}
        >
          {viewBookingContextObject.viewFlight  && (
            <div>
              <BookingContextProvider> <FlightLayout/> </BookingContextProvider>
            </div>
          )}
          {viewBookingContextObject.viewBooking && (
            <div>
             <BookingContextProvider> <BookingLayout />  </BookingContextProvider>
            </div>
          )}
          {/* {viewBookingContextObject.viewPassenger && (
            <div>
              <BookingContextProvider> <PassengerForm />  </BookingContextProvider>
            </div>
          )} */}
          {/* {viewBookingContextObject.viewSeat && (
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
              <BookingContextProvider> <FlightSeats />  </BookingContextProvider>
            </div>
          )} */}
        </Col>
      </Row>
    </Container>
  );
};

export default ViewUser;
