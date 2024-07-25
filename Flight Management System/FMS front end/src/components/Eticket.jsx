import React, { useState, useContext, useEffect } from "react";
import {
  Table,
  Button,
  Modal,
  ModalBody,
  ModalFooter,
  ModalHeader,
  ModalTitle,
} from "react-bootstrap";
import jsPDF from "jspdf";
import { BookingContext } from "../Context/BookingContext";
import axios from "axios";

const Eticket = () => {
  const bookingContextObject = useContext(BookingContext);
  const [bookings, setBookings] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [selectedEticket, setSelectedEticket] = useState(null);

  useEffect(() => {
    const fetchInitialData = async () => {
      try {
        await axios
          .get(
            "/api/eticket/bookingId/" +
              bookingContextObject.bookingObject.bookingId,
            {
              headers: {
                Authorization: window.sessionStorage.getItem("token"),
              },
            }
          )
          .then((response) => {
            console.log(response.data, "Response Data");
            setBookings(response.data);
          });
      } catch (error) {}
    };
    fetchInitialData();
  }, []);
  console.log(bookings, "Booking Data");
  const eticketList = bookings.map((eticket) => ({
    ticketId: `TK00${eticket.ticketId}`,
    bookingId: eticket.bookingID,
    flightId: `FL00${eticket.flighID}`,
    departure: eticket.departure,
    destination: eticket.destination,
    depDate: eticket.depDate,
    passengerName: eticket.passangerName,
    airline: eticket.airline,
    seatNo : eticket.seatNo
  }));

  console.log(eticketList);
  const downloadPdf = (eticket) => {

    const doc = new jsPDF();

    // Set font size and style
    doc.setFontSize(12);

    // Draw rectangle as header background
    doc.setFillColor("#4CAF50"); // Green color
    doc.rect(10, 10, 180, 20, "F");

    // Header text
    doc.setTextColor("#FFFFFF"); // White color
    doc.setFont("helvetica", "bold");
    doc.text(15, 20, "E-Ticket");

    // Content
    doc.setFont("helvetica", "normal");
    doc.setTextColor("#000000"); // Black color

    const startX = 15;
    let startY = 35;

    doc.text(startX , startY , "Booking ID:");
    doc.text(startX +50, startY  ,  eticket.bookingId.toString());

    startY += 15;

    doc.text(startX , startY , "Flight ID:");
    doc.text(startX +50, startY  ,  eticket.flightId.toString()); 

    startY += 15;

    doc.text(startX , startY , "Departure:");
    doc.text(startX +50, startY  ,  eticket.departure.toString()); 

    startY += 15;

    doc.text(startX , startY , "Destination:");
    doc.text(startX +50, startY  ,  eticket.destination.toString()); 

    startY += 15;

    doc.text(startX , startY , "Departure Date:");
    doc.text(startX +50, startY  ,  eticket.depDate.toString()); 

    startY += 15;

    doc.text(startX , startY , "Passenger Name:");
    doc.text(startX +50, startY  ,  eticket.passengerName.toString()); 

    startY += 15;

    doc.text(startX , startY , "Airline:");
    doc.text(startX +50, startY  ,  eticket.airline.toString()); 

    startY += 15;

    doc.text(startX , startY , "Seat No:");
    doc.text(startX +50, startY  ,  eticket.seatNo.toString());

    

    // Save the PDF
    doc.save(`eticket_${eticket.ticketId}.pdf`);
  };

  const handleShowModal = (eticket) => {
    setShowModal(true);
    setSelectedEticket(eticket);
  };

  const handleCloseModal = () => {
    setShowModal(false);
    setSelectedEticket(null);
  };

  return (
    <div className="px-3">
      <h1>E-Ticket List</h1>
      <Table>
        <thead>
          <tr>
            <th>Ticket ID</th>
            <th>Booking ID</th>
            <th>Flight ID</th>
            <th>Departure</th>
            <th>Destination</th>
            <th>Departure Date</th>
            <th>Passenger Name</th>
            <th>Airline</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {eticketList.map((eticket) => (
            <tr key={eticket.ticketId}>
              <td>{eticket.ticketId}</td>
              <td>{eticket.bookingId}</td>
              <td>{eticket.flightId}</td>
              <td>{eticket.departure}</td>
              <td>{eticket.destination}</td>
              <td>{eticket.depDate}</td>
              <td>{eticket.passengerName}</td>
              <td>{eticket.airline}</td>
              <td>
                <Button
                  variant="outline-secondary btn-sm"
                  onClick={() => handleShowModal(eticket)}
                >
                  View Details
                </Button>
              </td>
            </tr>
          ))}
        </tbody>
      </Table>

      <Modal show={showModal} onHide={handleCloseModal}>
        <ModalHeader closeButton>
          <ModalTitle>E-Ticket Details</ModalTitle>
        </ModalHeader>
        <ModalBody>
          <table className="table">
            <tbody>
              <tr>
                <th>Ticket ID</th>
                <td>{selectedEticket?.ticketId}</td>
              </tr>
              <tr>
                <th>Booking ID</th>
                <td>{selectedEticket?.bookingId}</td>
              </tr>
              <tr>
                <th>Flight ID</th>
                <td>{selectedEticket?.flightId}</td>
              </tr>
              <tr>
                <th>Departure</th>
                <td>{selectedEticket?.departure}</td>
              </tr>
              <tr>
                <th>Destination</th>
                <td>{selectedEticket?.destination}</td>
              </tr>
              <tr>
                <th>Departure Date</th>
                <td>{selectedEticket?.depDate}</td>
              </tr>
              <tr>
                <th>Passenger Name</th>
                <td>{selectedEticket?.passengerName}</td>
              </tr>
              <tr>
                <th>Airline</th>
                <td>{selectedEticket?.airline}</td>
              </tr>
              <tr>
                <th>Seat No</th>
                <td>{selectedEticket?.seatNo}</td>
              </tr>
            </tbody>
          </table>
          <Button
            variant="outline-dark"
            onClick={() => downloadPdf(selectedEticket)}
          >
            Download PDF
          </Button>
        </ModalBody>
        <ModalFooter>
          <Button variant="outline-secondary" onClick={handleCloseModal}>
            Close
          </Button>
        </ModalFooter>
      </Modal>
    </div>
  );
};

export default Eticket;
