import React, { useState } from "react";
import Accordion from "react-bootstrap/Accordion";
import ListGroup from "react-bootstrap/ListGroup";
import "../../css/Flight.css";
import { FaPlaneDeparture, FaPlaneArrival } from "react-icons/fa";
import PassengerForm from "../PassngersComponents/PassengerForm";
import { BookingContextProvider } from "../../Context/BookingContext";

function FlightCard({ flightObj = [] }) {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const iconSize = 30;

  const getDateAndMonth = (dateStr) => {
    const d = new Date(dateStr);
    return (
      d.getDate() +
      " " +
      d.toLocaleString("default", { month: "long" }) +
      " " +
      d.getFullYear()
    );
  };

  return (
    <div>
      <Accordion>
        <Accordion.Item eventKey="0">
          <Accordion.Header>
            <div direction="horizontal" className="container">
              <div className="row">
                <div className="col border-end my-auto">
                  <div className="p-2 text-center">
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
                      strokeWidth="2"
                      strokeLinecap="round"
                      strokeLinejoin="round"
                      className="feather feather-calendar"
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
                    : {getDateAndMonth(flightObj.departureDateAndTime)}
                  </div>
                </div>
                <div className="col my-auto">
                  <div className="p-2">
                    <div className="text-center">
                      <div
                        role="button"
                        className="btn btn-success"
                        onClick={() => setIsModalOpen(true)}
                        style={{ cursor: "pointer" }}
                      >
                        Book Flight
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </Accordion.Header>
          <Accordion.Body>
            <ListGroup>
              <ListGroup.Item className="text-start">
                Departure: {flightObj.departure}
              </ListGroup.Item>
              <ListGroup.Item className="text-start">
                Destination: {flightObj.destination}
              </ListGroup.Item>
              <ListGroup.Item className="text-start">
                Departure Time: {flightObj.departureDateAndTime.split("T")[1]}
              </ListGroup.Item>
              <ListGroup.Item className="text-start">
                Arrival Time: {flightObj.arrivalDateAndTime.split("T")[1]}
              </ListGroup.Item>
              <ListGroup.Item className="text-start">
                Airlines: {flightObj.airline}
              </ListGroup.Item>
              <ListGroup.Item className="text-start">
                Estimated Price: {flightObj.price}
              </ListGroup.Item>
            </ListGroup>
          </Accordion.Body>
        </Accordion.Item>
      </Accordion>

      {/* Modal */}
      {isModalOpen && (
        <div
          className="modal fade show"
          tabIndex="-1"
          style={{ display: "block" }}
          aria-hidden="true"
        >
          <div className="modal-dialog modal-dialog-center modal-xl">
            <div className="modal-content">
              <div className="modal-header">
                <h1 className="modal-title fs-5" id="exampleModalLabel">
                  Enter Passenger Details
                </h1>
                {/* Closing the modal */}
                <div
                  type="button"
                  className="btn-close"
                  onClick={() => setIsModalOpen(false)}
                ></div>
              </div>

              <div className="modal-body">
                {window.localStorage.getItem("isLoggedIn") === "true" ? (
                  <BookingContextProvider>
                    <PassengerForm
                      flightObj={flightObj}
                      setIsModalOpen={setIsModalOpen}
                    />
                  </BookingContextProvider>
                ) : (
                  <small className="text-danger">Login to continue</small>
                )}
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default FlightCard;
