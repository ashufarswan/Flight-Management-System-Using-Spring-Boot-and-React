import Accordion from "react-bootstrap/Accordion";
import Button from "react-bootstrap/Button";
import Stack from "react-bootstrap/Stack";
import ListGroup from "react-bootstrap/ListGroup";
import { useContext } from "react";
import { BookingContext } from "../../Context/BookingContext";
import { ViewBookingContext } from "../../Context/ViewBookingContext";
import usePayment from "./Payment";
import { useNavigate } from "react-router-dom";
import clock from "../../Assets/clock.svg";
import checkCircle from "../../Assets/check-circle.svg";
import cancel from "../../Assets/cancel.svg";
import axios from "axios";

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

function BookingAccordian({ booking }) {
  // console.log(typeof booking.bookingDateAndTime);
  // naivgate to Payment page
  // also setting booking Object in this
  const bookingContextObject = useContext(BookingContext);
  const viewBookingContextObject = useContext(ViewBookingContext);

  const handleCancelBooking = async () => {
    try {
      const updateResponse = await axios.put(
        `/api/booking/cancelBooking/` + booking.bookingId,
        {},
        {
          headers: {
            Authorization: window.sessionStorage.getItem("token"),
          },
        }
      );
      console.log(updateResponse);
      viewBookingContextObject.setviewFlight(true);
      viewBookingContextObject.setviewBooking(false);
    } catch (error) {
      console.log(error);
    }
  };

  const handlePaymentNavigation = () => {
    console.log("confirm booking called");
    bookingContextObject.setBookingObject(booking);
    viewBookingContextObject.setviewSeat(true);
    viewBookingContextObject.setviewBooking(false);
  };

  const navigate = useNavigate();
  const handleEticket = () => {
    console.log("View ticket");
    bookingContextObject.setBookingObject(booking);
    navigate("/view-eticket");
  };

  const getBookingStatus = () => {
    if (booking.bookingStatus === "created") {
      return <img src={clock} height={15} width={15} />;
    } else if (booking.bookingStatus === "paid") {
      return <img src={checkCircle} height={15} width={15} />;
    } else if (booking.bookingStatus === "cancelled") {
      return <img src={cancel} height={15} width={15} />;
    } else if (booking.bookingStatus === "refunded") {
      return <img src={cancel} height={15} width={15} />;
    }
  };

  const getButtons = () => {
    if (booking.bookingStatus === "created") {
      return (
        <Button variant="outline-secondary" onClick={handlePaymentNavigation}>
          Confirm
        </Button>
      );
    } else if (booking.bookingStatus === "paid") {
      return (
        <Button variant="outline-secondary" onClick={handleEticket}>
          View Ticket
        </Button>
      );
    } else if (booking.bookingStatus === "refunded") {
      return (
        <Button variant="success" onClick={handleEticket} disabled>
          {booking.bookingStatus}
        </Button>
      );
    } else {
      return (
        <Button
          variant="danger text-uppercase fw-bold"
          onClick={handleEticket}
          disabled
        >
          {booking.bookingStatus}
        </Button>
      );
    }
  };

  return (
    <Accordion>
      <Accordion.Item eventKey="0">
        <Accordion.Header style={{ width: "100%" }}>
          <div className="row">
            <div className="col border-end">{booking.bookingId}</div>

            <div className="col  border-end">{booking.flight.departure}</div>

            <div className="col  border-end">{booking.flight.destination}</div>

            <div className="col-3  border-end">
              {formatReadableDate(booking.flight.departureDateAndTime, true)}
            </div>
            <div className="col  border-end">{getBookingStatus()}</div>

            <div className="col">{getButtons()}</div>
          </div>
        </Accordion.Header>
        <Accordion.Body>
          <ListGroup>
            <ListGroup.Item>AirLines : {booking.flight.airline}</ListGroup.Item>
            <ListGroup.Item>
              Date of Booking :{" "}
              {formatReadableDate(booking.bookingDateAndTime, false)}
            </ListGroup.Item>
            <ListGroup.Item>
              Passengers Travelling : {booking.numberOfPassengers}
            </ListGroup.Item>
            <ListGroup.Item>
              Estimated Price : {booking.flight.price}
            </ListGroup.Item>
            {booking.bookingStatus === "cancelled" ||
            booking.bookingStatus === "refunded" ? (
             ""
            ) : (
              <ListGroup.Item style={{ textAlign: "right" }}>
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
  );
}

export default BookingAccordian;
