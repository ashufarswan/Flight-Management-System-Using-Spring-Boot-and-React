import React, { useContext } from "react";
import Accordion from "react-bootstrap/Accordion";
import Stack from "react-bootstrap/Stack";
import Button from "react-bootstrap/Button";
import ListGroup from "react-bootstrap/ListGroup";
import { BookingContext } from "../../Context/BookingContext";
import { useNavigate } from "react-router-dom";
import { ViewBookingContext } from "../../Context/ViewBookingContext";
import "../../css/Flight.css";
import {
  FaPlaneDeparture,
  FaPlaneArrival,
  FaCalendarAlt,
} from "react-icons/fa";

function FlightCard({ flightObj = [] }) {
  const iconSize = 30;
  const navigate = useNavigate();
  const bookingContextObject = useContext(BookingContext);
  const viewBookingContextObject = useContext(ViewBookingContext);

  const handleToNavigateToPassengerDetails = () => {
    console.log(
      "inside navigateToPassengerDteails inside Flight card",
      "Setting flightObject to Booking context"
    );
    bookingContextObject.setFlightObject(flightObj);
    viewBookingContextObject.setviewFlight(false);
    viewBookingContextObject.setviewPassenger(true);
    // navigate("/passenger-details");
  };
  const getDateAndMonth = (dateStr) => {
    const d = new Date(dateStr);
    return d.getDate()+" "+d.toLocaleString('default', { month: 'long' })+" "+d.getFullYear();
  }

  return (
    <div>
    <Accordion>
      <Accordion.Item eventKey="0">
        <Accordion.Header>
          <div direction="horizontal" className="container">
            <div className="row">
              <div className="col border-end my-auto">
                <div className="p-2 text-center">
                  {" "}
                  <FaPlaneDeparture size={iconSize} color="green" /> :{" "}
                  {flightObj.departure}
                </div>
              </div>
              <div className="col border-end my-auto">
                <div className="p-2 text-center">
                  <FaPlaneArrival size={iconSize} color="grey" /> :{" "}
                  {flightObj.destination}
                </div>
              </div>
              <div className="col border-end my-auto">
                <div className="p-2 text-center">
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    width="24"
                    height="24"
                    viewBox="0 0 24 24"
                    fill="none"
                    stroke="currentColor"
                    stroke-width="2"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    class="feather feather-calendar"
                  >
                    <rect
                      x="3"
                      y="4"
                      width="18"
                      height="18"
                      rx="2"
                      ry="2"
                    ></rect>
                    <line x1="16" y1="2" x2="16" y2="6"></line>
                    <line x1="8" y1="2" x2="8" y2="6"></line>
                    <line x1="3" y1="10" x2="21" y2="10"></line>
                  </svg>{" "}
                  :{" "}
                  {getDateAndMonth(flightObj.departureDateAndTime)}
                </div>
              </div>
              <div className="col my-auto">
                <div className="p-2">
                  <div className="text-center">
                    {/* Change it to link  with FLight Object passed to it*/}
                    <Button
                      variant="success"
                      onClick={handleToNavigateToPassengerDetails}
                    >
                      Book Flight
                    </Button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </Accordion.Header>
        <Accordion.Body>
          <ListGroup>
            <ListGroup.Item>Departure : {flightObj.departure}</ListGroup.Item>
            <ListGroup.Item>
              Destination : {flightObj.destination}
            </ListGroup.Item>
            <ListGroup.Item>
              Departure Time : {flightObj.departureDateAndTime.split("T")[1]}
            </ListGroup.Item>
            <ListGroup.Item>
              Arrival Time : {flightObj.arrivalDateAndTime.split("T")[1]}
            </ListGroup.Item>
            <ListGroup.Item>AirLines : {flightObj.airline}</ListGroup.Item>
            <ListGroup.Item>Estimated Price : {flightObj.price}</ListGroup.Item>
          </ListGroup>
        </Accordion.Body>
      </Accordion.Item>
    </Accordion>
    </div>
  );
}

export default FlightCard;
