import React from "react";
import Accordion from "react-bootstrap/Accordion";
import Button from "react-bootstrap/Button";
import ListGroup from "react-bootstrap/ListGroup";
import { useContext, useState } from "react";
import BookingContext from "../../Context/BookingContext";
import { ViewBookingContext } from "../../Context/ViewBookingContext";
import { useNavigate } from "react-router-dom";
import clock from "../../Assets/clock.svg";
import checkCircle from "../../Assets/check-circle.svg";
import cancel from "../../Assets/cancel.svg";
import axios from "axios";
import Eticket from "../Eticket";
import FlightSeats from "../SeatSelectionComponents/FlightSeats";

function formatReadableDate(dateString, withTime) {
  const date = new Date(dateString);
  if (withTime) {
    const options = {
      weekday: "long", // Display full name of the day (e.g., Monday)
      year: "numeric", // Display full year (e.g., 2023)
      month: "long", // Display full name of the month (e.g., July)
      day: "numeric", // Display the day of the month (e.g., 12)
      hour: "numeric", // Display the hour (e.g., 12)
      minute: "numeric", // Display the minute (e.g., 00)
      second: "numeric", // Display the second (e.g., 00)
      timeZoneName: "short", // Display the timezone abbreviation (e.g., GMT)
    };
    let timeString = date.toLocaleTimeString().split(":");
    let time =
      timeString[0] + ":" + timeString[1] + " " + timeString[2].split(" ")[1];
    return date.toDateString().slice(4) + " " + time;
    // return date.toLocaleString("en-US", options);
  }
  const options = {
    weekday: "long", // Display full name of the day (e.g., Monday)
    year: "numeric", // Display full year (e.g., 2023)
    month: "long", // Display full name of the month (e.g., July)
    day: "numeric", // Display the day of the month (e.g., 12)
  };
  return date.toLocaleString("en-US", options);
}

function BookingAccordion({ booking }) {
  const viewBookingContextObject = useContext(ViewBookingContext);
  const [isEticketModalOpen, setIsEticketModalOpen] = useState(false);
  const [activeModalBookingId, setActiveModalBookingId] = useState(null);

  const handleConfirmClick = (bookingId) => {
    setActiveModalBookingId(bookingId); // Set the current booking ID
    viewBookingContextObject.setviewSeatModal(true);
  };
  
  
  //console.log("Booking accordian", booking);
  const handleCancelBooking = async () => {
    try {
      const updateResponse = await axios.put(
        `/api/booking/cancelBooking/${booking.bookingId}`,
        {},
        {
          headers: {
            Authorization: window.sessionStorage.getItem("token"),
          },
        }
      );
      //console.log(updateResponse);
      viewBookingContextObject.setviewFlight(true);
      viewBookingContextObject.setviewBooking(false);
    } catch (error) {
      //console.log(error);
    }
  };

  // const handlePaymentNavigation = () => {
  //   //console.log(booking);
  //   setBookingObject(booking);
  //   viewBookingContextObject.setviewSeat(true);
  //   viewBookingContextObject.setviewBooking(false);
  // };

  const getBookingStatus = () => {
    const statusIcons = {
      created: clock,
      paid: checkCircle,
      cancelled: cancel,
      refunded: cancel,
    };
    return (
      <img
        src={statusIcons[booking.bookingStatus]}
        height={15}
        width={15}
        alt="Status Icon"
      />
    );
  };

  const getButtons = () => {
    if (booking.bookingStatus === "created") {
      return (
        // <Button variant="outline-secondary" onClick={handlePaymentNavigation}>
        //   Confirm
        // </Button>
        <Button
          variant="outline-secondary"
          onClick={() => handleConfirmClick(booking.bookingId)}
        >
          Confirm
        </Button>
      );
    } else if (booking.bookingStatus === "paid") {
      return (
        <Button variant="success" onClick={() => setIsEticketModalOpen(true)}>
          View Ticket
        </Button>
      );
    } else {
      return (
        <Button
          variant={booking.bookingStatus === "refunded" ? "success" : "danger"}
          disabled
        >
          {booking.bookingStatus.toUpperCase()}
        </Button>
      );
    }
  };

  return (
    <div>
      <Accordion>
        <Accordion.Item eventKey="0">
          <Accordion.Header>
            <div className="row">
              <div className="col border-end">{booking.bookingId}</div>
              <div className="col border-end">{booking.flight.departure}</div>
              <div className="col border-end">{booking.flight.destination}</div>
              <div className="col-3 border-end">
                {formatReadableDate(booking.flight.departureDateAndTime, true)}
              </div>
              <div className="col border-end">{getBookingStatus()}</div>
              <div className="col">{getButtons()}</div>
            </div>
          </Accordion.Header>
          <Accordion.Body>
            <ListGroup>
              <ListGroup.Item className="text-start">
                Airlines: {booking.flight.airline}
              </ListGroup.Item>
              <ListGroup.Item className="text-start">
                Date of Booking:{" "}
                {formatReadableDate(booking.bookingDateAndTime, false)}
              </ListGroup.Item>
              <ListGroup.Item className="text-start">
                Passengers Traveling: {booking.numberOfPassengers}
              </ListGroup.Item>
              <ListGroup.Item className="text-start">
                Estimated Price: {booking.flight.price}
              </ListGroup.Item>
              {booking.bookingStatus === "cancelled" ||
              booking.bookingStatus === "refunded" ? null : (
                <ListGroup.Item className="text-end">
                  <Button
                    variant="outline-danger"
                    onClick={handleCancelBooking}
                  >
                    Cancel Booking
                  </Button>
                </ListGroup.Item>
              )}
            </ListGroup>
          </Accordion.Body>
        </Accordion.Item>
      </Accordion>

      {isEticketModalOpen && (
        <div
          className="modal fade show"
          tabIndex="-1"
          style={{ display: "block", backgroundColor: "rgba(0, 0, 0, 0.5)" }}
        >
          <div className="modal-dialog modal-dialog-centered modal-xl">
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title">Etickets</h5>
                <button
                  type="button"
                  className="btn-close"
                  onClick={() => setIsEticketModalOpen(false)}
                ></button>
              </div>
              <div className="modal-body">
                <Eticket booking={booking} />
              </div>
            </div>
          </div>
        </div>
      )}
      
      {activeModalBookingId === booking.bookingId && viewBookingContextObject.viewSeatModal && (
        <div
          className="modal fade show"
          tabIndex="-1"
          style={{ display: "block"}}
        >
          <div className="modal-dialog modal-dialog-centered modal-xl">
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title">Seat Selection</h5>
                <button
                  type="button"
                  className="btn-close"
                  onClick={() => viewBookingContextObject.setviewSeatModal(false)}
                ></button>
              </div>
              <div className="modal-body">
                <FlightSeats booking={booking}  />
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default BookingAccordion;