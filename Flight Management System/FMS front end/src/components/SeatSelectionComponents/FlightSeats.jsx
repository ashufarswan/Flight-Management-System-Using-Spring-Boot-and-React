import React, { useContext, useEffect, useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import "./FlightSeats.css"; // Make sure to include your CSS here

import Button from "react-bootstrap/esm/Button";
import { getAllSeats } from "./SeatsApiAccess";
import { BookingContext } from "../../Context/BookingContext";
import ColorBox from "./ColorBox";
import usePayment from "../BookingsComponents/Payment";

const Seat = ({ isSelected, onClick, disabled }) => (
  // if isSelected true 'seat selected' css will be applied else 'seat' class css is applied
  <div
    className={`seat ${isSelected ? "selected" : ""} ${
      disabled ? "disabled" : ""
    }`}
    onClick={!disabled ? onClick : null}
  ></div>
);

const FlightSeats = () => {
  const bookingContextObject = useContext(BookingContext);
  const { handlePayment } = usePayment();

  const rows = 12;
  const columns = 6;
  const [selectedSeats, setSelectedSeats] = useState(
    Array(rows)
      .fill()
      .map(() => Array(columns).fill(false))
  );

  const [bookedSeats, setBookedSeats] = useState([]);


  useEffect(() => {
    getAllSeats(bookingContextObject.bookingObject.flight.flightId).then((result) => {
      console.log(result, Array.isArray(result));
      setBookedSeats(result);
    });
  }, [bookingContextObject.bookingObject.flight.flightId]);

  const updateSeatsToApi = () => {
  
    handlePayment(bookingContextObject.bookingObject.bookingId,selectedSeats)
   
  };

  // seat definition
  // seatNumber --> Column number [A-Z] 0 - A , 25 - Z
  // seatRowNumber --> Row number [1-12] 0 -1 , 11 - 12
  // seatClassType --> Economy, Business, First
  // isOccupied --> true or false
  // seatType --> Window, Aisle, Middle

 

  function seatObjectToIndexes(seatObj) {
    const row = seatObj.seatRowNumber;
    const col = seatObj.seatNumber;
    //const isSelected = seatObj.isOccupied
    return { bookedRow: row, bookedCol: col };
  }

  function isDisabled(row, col) {
    // console.log("log for isDisabled()", bookedSeats)
    for (const bookedSeat of bookedSeats) {
      const { bookedRow, bookedCol } = seatObjectToIndexes(bookedSeat);

      if (bookedRow === row && bookedCol === col) {
        console.log(`return true for row ${row} col ${col}`);
        return true;
      }
    }
    return false;
  }

  const handleSeatClick = (row, col) => {
    console.log(row, col);
    const newSelectedSeats = [...selectedSeats];
    // set the seat value to be selected
    newSelectedSeats[row][col] = !newSelectedSeats[row][col];
    // const seatObj = indexToSeatObject(row, col, newSelectedSeats[row][col])
    // console.log(seatObj)
    // console.log(seatObjectToIndexes(seatObj))
    setSelectedSeats(newSelectedSeats);
  };

  return (
    <React.Fragment>
      <div className="divContainingFlight">
        <h1 className="display-6">Select Your Seats</h1>
        <div className="colorBoxHolder floatingContainer">
          <ColorBox color="#F37575" description="Already Booked Seats" />
          <ColorBox color="transparent" description="Availiable Seats " />
          <ColorBox color="#B0E7C1" description="Selected Seats " />
          <div className="text-center">
            <Button id="confirmSeat"  variant="outline-secondary mb-3" onClick={updateSeatsToApi}>
              Confirm Seats
            </Button>
          </div>
        </div>
        <div className="FlightSeats">
          {Array.from({ length: rows }).map((_, rowIndex) => (
            <div className="seat-row" key={rowIndex}>
              {Array.from({ length: columns }).map((_, colIndex) => (
                <React.Fragment key={colIndex}>
                  {colIndex === 3 && <div className="aisle"></div>}
                  <Seat
                    isSelected={selectedSeats[rowIndex][colIndex]}
                    onClick={() => handleSeatClick(rowIndex, colIndex)}
                    disabled={isDisabled(rowIndex, colIndex)}
                  />
                </React.Fragment>
              ))}
            </div>
          ))}
        </div>
      </div>
    </React.Fragment>
  );
};

export default FlightSeats;
