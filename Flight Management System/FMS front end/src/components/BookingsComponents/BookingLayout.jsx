import React, { useEffect, useState } from "react";
import BookingAccordian from "./BookingAccordian";
import {
  getBookingsByUserId,
  getUserName
} from "./BookingApiAcess";
import Stack from "react-bootstrap/esm/Stack";
import { useNavigate } from "react-router-dom";
import "./../../css/Flight.css";
import { BookingContextProvider } from "../../Context/BookingContext";

function BookingLayout() {
  const [userBookings, setUserBookings] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const token = sessionStorage.getItem('token');

    if (!token) {
      navigate('/');
    }
  }, [navigate]);
   useEffect(() => {
    // if something goes wrong flightObject is still null navigate to view-flights
    // if(bookingContextObject.flightObject == null || bookingContextObject.passengerObjects.length == 0){
    //     console.error(bookingContextObject.flightObject, bookingContextObject.passengerObjects)
    //     window.alert("something went wrong")
    //     bookingContextObject.setFlightObject(null);
    //     bookingContextObject.setPassengerObjects([]);
    //     navigateHook("/view-flights");
    // }

    getBookingsByUserId().then((data) => {
      //console.log(typeof data, data);
      setUserBookings(data);
    });
  }, []);
  

  return (
    <div className="layoutBooking">
      <h4 className="h3 mb-5">
        <span className="text-secondary h3 ">Bookings for User :</span>{" "}
        <b className="h3">{getUserName().charAt(0).toUpperCase() + getUserName().slice(1)}</b>
      </h4>
      <div className="row" style={{ margin: "0 0.5px" }}>
        <div className="col">
          <h6 className="fw-semibold">Booking Number</h6>
        </div>
        <div className="col">
          <h6
            className="fw-semibold"
            style={{ position: "relative", left: "-2px" }}
          >
            From
          </h6>
        </div>
        <div className="col">
          <h6
            className="fw-semibold"
            style={{ position: "relative", left: "-15px" }}
          >
            To
          </h6>
        </div>
        <div className="col-3">
          <h6
            className="fw-semibold"
            style={{ position: "relative", left: "-15px" }}
          >
            Departure Date and Time
          </h6>
        </div>
        <div className="col">
          <h6
            className="fw-semibold"
            style={{ position: "relative", left: "-15px" }}
          >
            Status
          </h6>
        </div>
        <div className="col">
          <h6 className="fw-semibold">Action</h6>
        </div>
      </div>
      <Stack className="pb-3" gap={3}>
        {userBookings.map((booking, index) => {
          return (
            <div className="flight-card-layout">
              <div className="flight-card">
               <BookingContextProvider> <BookingAccordian key={index} booking={booking} /> </BookingContextProvider>
              </div>
            </div>
          );
        })}
      </Stack>
    </div>
  );
}

export default BookingLayout;
