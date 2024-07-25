import React, { useRef, useState } from "react";
import InputGroup from "react-bootstrap/InputGroup";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";

function AdminFlightSearchBox({ getFliteredFlights }) {
  const [date, setDate] = useState("");
  const departureRef = useRef(null);
  const destinationRef = useRef(null);
  const maxPriceRef = useRef(null);

  const handleSearchFlights = () => {
    let departure = departureRef.current.value;
    let destination = destinationRef.current.value;
    let maxPrice = maxPriceRef.current.value;
    // add method for api call
    getFliteredFlights({
      departureValue: departure,
      destinationValue: destination,
      dateValue: date,
      maxPriceValue: maxPrice,
    });
  };

  return (
    <div className="inside-box-flex mx-5">
      <div>
      <InputGroup className="mb-3">
        <InputGroup.Text>From</InputGroup.Text>
        <input
            aria-label="From"
            className="form-control"
            ref={departureRef}
          />
        <InputGroup.Text>To</InputGroup.Text>
        <input
            aria-label="To"
            className="form-control"
            ref={destinationRef}
          />
      </InputGroup>

      <InputGroup className="mb-3">
        <InputGroup.Text>Date</InputGroup.Text>
        <Form.Control
          type="date"
          name="datepic"
          placeholder="DateRange"
          value={date}
          onChange={(e) => setDate(e.target.value)}
        />

        <InputGroup.Text>Under price</InputGroup.Text>
        <Form.Control aria-label="Price" type="number" ref={maxPriceRef} />
      </InputGroup>
      </div>
      <div >
        <Button variant="outline-secondary mt-2" onClick={handleSearchFlights}>
          Search 
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="15"
            height="15"
            viewBox="0 0 24 24"
            fill="none"
            stroke="currentColor"
            strokeWidth="3"
            strokeLinecap="round"
            strokeLinejoin="round"
            className="feather feather-search ms-1"
          >
            <circle cx="11" cy="11" r="8"></circle>
            <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
          </svg>
        </Button>
      </div>
    </div>
  );
}

export default AdminFlightSearchBox;
