import React, { useContext } from "react";
import Accordion from "react-bootstrap/Accordion";
import Button from "react-bootstrap/Button";
import ListGroup from "react-bootstrap/ListGroup";
import { deleteFlight } from "./FlightAPIAccess";
import { flightContext, updateContext } from "../App";
import "../../css/Flight.css";
import {
  FaPlaneDeparture,
  FaPlaneArrival
} from "react-icons/fa";

function AdminFlightCard({ flightObj = [], updateFlights }) {
  const iconSize = 30;
  const [updateFlight, setUpdateFlight] = useContext(updateContext);
  const [flightId, setFlightId] = useContext(flightContext);

  const handleDeleteFlight = () => {
    const bool =  deleteFlight(flightObj.flightId);
    bool.then((res)=>{
      if(res){
        updateFlights(flightObj.flightId);
      }
    })
   
    
  };

  const handleUpdateFlight = () => {
    console.log("Flight Card handle update")
    setFlightId(flightObj.flightId);
    setUpdateFlight(true);
  };

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
                <div className="d-flex">
                  <div className="p-2">
                    <div>
                      {/* Change it to link  with FLight Object passed to it*/}
                      <Button
                        variant="success btn-sm"
                        onClick={handleUpdateFlight}

                      >Update
                        <svg
                        style={{position: "relative", top: "-1px", left: "4px"}}
                          xmlns="http://www.w3.org/2000/svg"
                          width="13"
                          height="13"
                          viewBox="0 0 24 24"
                          fill="none"
                          stroke="currentColor"
                          strokeWidth="2"
                          strokeLinecap="round"
                          strokeLinejoin="round"
                          className="feather feather-edit"
                        >
                          <path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"></path>
                          <path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"></path>
                        </svg>
                      </Button>
                    </div>
                  </div>
                  <div className="col my-auto">
                    <div className="p-2">
                      <div>
                        {/* Change it to link  with FLight Object passed to it*/}
                        <Button
                          variant="danger text-uppercase fw-bold btn-sm"
                          // style={{ width: "55%" }}
                          onClick={handleDeleteFlight}
                          title="Delete"
                        >
                          <svg
                            xmlns="http://www.w3.org/2000/svg"
                            width="13"
                            height="13"
                            viewBox="0 0 24 24"
                            fill="none"
                            stroke="currentColor"
                            strokeWidth="2"
                            strokeLinecap="round"
                            strokeLinejoin="round"
                            className="feather feather-trash-2"
                            
                          >
                            <polyline points="3 6 5 6 21 6"></polyline>
                            <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path>
                            <line x1="10" y1="11" x2="10" y2="17"></line>
                            <line x1="14" y1="11" x2="14" y2="17"></line>
                          </svg>
                          
                        </Button>
                      </div>
                    </div>
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
              Departure Time : {flightObj.departureDateAndTime}
            </ListGroup.Item>
            <ListGroup.Item>
              Arrival Time : {flightObj.arrivalDateAndTime}
            </ListGroup.Item>
            <ListGroup.Item>AirLines : {flightObj.airline}</ListGroup.Item>
            <ListGroup.Item>Estimated Price : {flightObj.price}</ListGroup.Item>
          </ListGroup>
        </Accordion.Body>
      </Accordion.Item>
    </Accordion>
  );
}

export default AdminFlightCard;
