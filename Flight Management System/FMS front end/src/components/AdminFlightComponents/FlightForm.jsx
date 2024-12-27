import React, { useEffect, useState,useCallback } from "react";
import axios from "axios";
import {
  Form,
  FormGroup,
  FormLabel,
  FormControl,
  FormCheck,
} from "react-bootstrap";
import "../../css/FlightForm.css";
import { toast } from "react-toastify";
import { useNavigate } from "react-router-dom";

const notifyerror = (str) => {
  toast.error(str);
};

const notifysuccess = () => {
  toast.success("Flight added successfully.");
};

let adminToken = window.sessionStorage.getItem("token");

function FlightForm({setFlights}) {
  const [departure, setDeparture] = useState("");
  const [destination, setDestination] = useState("");
  const [departureDateAndTime, setDepartureDateAndTime] = useState("");
  const [arrivalDateAndTime, setArrivalDateAndTime] = useState("");
  const [duration, setDuration] = useState("");
  const [airline, setAirline] = useState("");
  const [airlineType, setAirlineType] = useState("domestic");
  const [price, setPrice] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  
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

  const calculateDuration  = useCallback(() => {
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
  }, [departureDateAndTime, arrivalDateAndTime]);

  useEffect(() => {
    calculateDuration();
  }, [calculateDuration]);

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
  //console.log(formdata);

  const [isValid, setIsValid] = useState(true); // Initially assume price is valid
  const handlePriceChange = (e) => {
    const { value } = e.target;
    // Example validation: Check if value is a number and positive
    const isValidPrice =
      /^\d+(\.\d{1,2})?$/.test(value) && parseFloat(value) >= 500;
    setIsValid(isValidPrice);
    setPrice(value);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    adminToken = window.sessionStorage.getItem("token");
    await axios
      .post("/api/flight", formdata, { headers: { Authorization: adminToken } })
      .then((response) => {
        notifysuccess();
        let data__ = response.data.data
        //console.log(data__)
        setFlights(prevFlights => [...prevFlights,data__]);
        navigate("/admin");
      })
      .catch((error) => {
        //console.log(error);
        notifyerror();
      });
  };

  const getCurrentDateTime = () => {
    const now = new Date();
    const year = now.getFullYear();
    const month = String(now.getMonth() + 1).padStart(2, '0');
    const day = String(now.getDate()).padStart(2, '0');
    const hours = String(now.getHours()).padStart(2, '0');
    const minutes = String(now.getMinutes()).padStart(2, '0');
 
    return `${year}-${month}-${day}T${hours}:${minutes}`;
  };
  return (
    <div className="flight-form-container">
      <center>
        <h2>Add Flight</h2>
      </center>
      <Form className="flightForm" onSubmit={handleSubmit}>
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
            value={departureDateAndTime}
            min = {getCurrentDateTime()}
            onChange={(event) => {
              setDepartureDateAndTime(event.target.value);
              ////console.log("DepDate:"+departureDateAndTime)
              //calculateDuration();
            }}
          />
        </FormGroup>

        <FormGroup controlId="arrivalDateAndTime">
          <FormLabel>Arrival Date and Time</FormLabel>
          <FormControl
            type="datetime-local"
            value={arrivalDateAndTime}
            min = {getCurrentDateTime()}
            onChange={(event) => {
              setArrivalDateAndTime(event.target.value);
              //calculateDuration();
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
          <FormControl
            type="text"
            value={duration / 60 + " Hr"}
            required
            readOnly
          />
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
            onChange={handlePriceChange}
            placeholder="Enter price"
            isInvalid={!isValid}
            required
          />
          <Form.Control.Feedback type="invalid">
            Please enter price greater than 500.
          </Form.Control.Feedback>
        </FormGroup>

        <button type="submit" className="btn btn-outline-secondary mt-3">
          Submit
        </button>
      </Form>
    </div>
  );
}

export default FlightForm;
