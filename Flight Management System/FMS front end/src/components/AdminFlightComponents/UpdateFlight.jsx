import React, { useContext, useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import axios from "axios";
import {
  Form,
  FormGroup,
  FormLabel,
  FormControl,
  FormCheck,
  Button,
} from "react-bootstrap";
import "../../css/FlightForm.css";
import { toast } from "react-toastify";
import { flightContext, updateContext } from "../App";

const notifyerror = (str) => {
  toast.error(str);
};

const notifysuccess = () => {
  toast.success("Flight Updated successfully.");
};

const adminToken = window.sessionStorage.getItem("token");

function UpdateFlight(props) {

  const [updateFlight, setUpdateFlight] = useContext(updateContext);
  const [flightId, setFlightId] = useContext(flightContext);
  // const location = useLocation();
  // const navigate = useNavigate();
  console.log(flightId)
  console.log(props,"Update")
  const data = props.data.filter((obj) => obj.flightId === flightId)[0];

  // const data = "";
  
  const [departure, setDeparture] = useState(data.departure);
  const [destination, setDestination] = useState(data.destination);
  const [departureDateAndTime, setDepartureDateAndTime] = useState(
    data.departureDateAndTime
  );
  console.log(departure)
  const [arrivalDateAndTime, setArrivalDateAndTime] = useState(
    data.arrivalDateAndTime
  );
  const [duration, setDuration] = useState(data.duration);
  const [airline, setAirline] = useState(data.airline);
  const [airlineType, setAirlineType] = useState(data.airlineType);
  const [price, setPrice] = useState(data.price);
  const [error, setError] = useState("");
  const navigate = useNavigate();

  
  useEffect(() => {
    calculateDuration();
    // console.log(typeof departureDateAndTime);
  }, [departureDateAndTime, arrivalDateAndTime]);

  function formatDate(sdate) {
    const date = new Date(sdate);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, "0");
    const day = String(date.getDate()).padStart(2, "0");
    const hours = String(date.getHours()).padStart(2, "0");
    const minutes = String(date.getMinutes()).padStart(2, "0");
    const seconds = String(date.getSeconds()).padStart(2, "0");
    const milliseconds = String(date.getMilliseconds()).padStart(3, "0");

    return `${year}-${month}-${day}T${hours}:${minutes}:${seconds}.${milliseconds}`;
  }

  const calculateDuration = () => {
    if (departureDateAndTime && arrivalDateAndTime) {
      const departureDateTime = new Date(departureDateAndTime);
      const arrivalDateTime = new Date(arrivalDateAndTime);
      if (departureDateTime > arrivalDateTime) {
        setError(
          "Departure date and time cannot be greater than arrival date and time"
        );
      } else {
        const durationMilliseconds =
          arrivalDateTime.getTime() - departureDateTime.getTime();
        const hours = Math.floor(durationMilliseconds / 3600000);
        const minutes = Math.floor((durationMilliseconds % 3600000) / 60000);
        setDuration(hours * 60 + minutes);
        setError("");
      }
    }
  };
  const formdata = {
    departure: departure,
    destination: destination,
    departureDateAndTime: formatDate(departureDateAndTime),
    arrivalDateAndTime: formatDate(arrivalDateAndTime),
    duration: duration,
    airline: airline,
    airlineType: airlineType,
    price: price,
  };
  // console.log(formdata);
  const handleupdate = async (event) => {
    event.preventDefault();
    console.log("Updating FLight")
    await axios
      .put("/api/flight" + "/" + data.flightId, formdata, {
        headers: { Authorization: window.sessionStorage.getItem("token") },
      })
      .then(() => {
        notifysuccess();
      })
      .catch((error) => {
        console.log(error);
        notifyerror();
      });
      setUpdateFlight(false);
      // navigate("/flight");

  };
  return (
    <div className="flight-form-container">
      <center>
        <h2>Update Flight</h2>
      </center>
      <Form className="flightForm" onSubmit={handleupdate}>
        <FormGroup controlId="departure">
          <FormLabel>Departure</FormLabel>
          <FormControl
            type="text"
            value={departure}
            required
            onChange={(event) => setDeparture(event.target.value)}
            placeholder="Enter departure city"
          />
        </FormGroup>

        <FormGroup controlId="destination">
          <FormLabel>Destination</FormLabel>
          <FormControl
            type="text"
            value={destination}
            required
            onChange={(event) => setDestination(event.target.value)}
            placeholder="Enter destination city"
          />
        </FormGroup>

        <FormGroup controlId="departureDateAndTime">
          <FormLabel>Departure Date and Time</FormLabel>
          <FormControl
            type="datetime-local"
            value={new Date(departureDateAndTime).toISOString().slice(0, 16)}
            onChange={(event) => {
              setDepartureDateAndTime(event.target.value);
              // console.log("DepDate:"+departureDateAndTime)
              calculateDuration();
            }}
          />
          
        </FormGroup>

        <FormGroup controlId="arrivalDateAndTime">
          <FormLabel>Arrival Date and Time</FormLabel>
          <FormControl
            type="datetime-local"
            value={new Date(arrivalDateAndTime).toISOString().slice(0, 16)}
            onChange={(event) => {
              setArrivalDateAndTime(event.target.value);
              calculateDuration();
            }}
          />
        </FormGroup>

        {error && (
          <div style={{ color: "red" }}>
            <p>{error}</p>
          </div>
        )}
        <FormGroup controlId="duration">
          <FormLabel>Duration</FormLabel>
          <FormControl type="text" value={duration/60 + " Hr"} required readOnly />
        </FormGroup>

        <FormGroup controlId="airline">
          <FormLabel>Airline</FormLabel>
          <FormControl
            type="text"
            value={airline}
            required
            onChange={(event) => setAirline(event.target.value)}
            placeholder="Enter airline name"
          />
        </FormGroup>

        <FormGroup controlId="airlineType">
          <FormLabel>Airline Type</FormLabel>
          <FormCheck
            type="radio"
            name="airlineType"
            value="domestic"
            required
            checked={airlineType === "domestic"}
            onChange={(event) => setAirlineType(event.target.value)}
            label="Domestic"
          />
          <FormCheck
            type="radio"
            name="airlineType"
            value="international"
            required
            checked={airlineType === "international"}
            onChange={(event) => setAirlineType(event.target.value)}
            label="International"
          />
        </FormGroup>

        <FormGroup controlId="price">
          <FormLabel>Price</FormLabel>
          <FormControl
            type="number"
            value={price}
            required
            onChange={(event) => setPrice(event.target.value)}
            placeholder="Enter price"
          />
        </FormGroup>

        <button type="submit" className="btn btn-outline-secondary mt-3">Update</button>
        </Form>
    </div>
  );
}

export default UpdateFlight;
