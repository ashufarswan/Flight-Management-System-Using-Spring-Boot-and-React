import React, { useContext, useEffect, useState } from "react";
import { Form, Button, Container, Row, Col } from "react-bootstrap";
import { postPassengerDetails, createBooking } from "./PassengerApiAccess";
import { BookingContext } from "../../Context/BookingContext";
import { useNavigate } from "react-router-dom";
import Modal from "react-bootstrap/Modal";
import { ViewBookingContext } from "../../Context/ViewBookingContext";
 
const PassengerForm = () => {
  const [showConfirmationPopUp, setShowConfirmationPopUp] = useState(false);
  const bookingContextObject = useContext(BookingContext);
  const viewBookingContextObject = useContext(ViewBookingContext);
 
  const navigateHook = useNavigate();
 
  const [numPassengers, setNumPassengers] = useState(0);
  const [currentPassenger, setCurrentPassenger] = useState(0);
  const [passengerDetails, setPassengerDetails] = useState([]);
  const [validated, setValidated] = useState(false);
 
  const handleNumPassengersChange = (e) => {
    try {
      const num = parseInt(e.target.value, 10);
      setNumPassengers(num);
      setPassengerDetails(
        Array(num).fill({
          firstName: "",
          lastName: "",
          emailId: "",
          phoneNumber: "",
          dateOfBirth: "",
          nationality: "",
          passportIdNumber: "",
          gender: "",
          seatPreference: "",
        })
      );
    } catch (error) {
      console.log(error);
    }
  };
 
  const handleChange = (e, index) => {
    const { name, value } = e.target;
    const details = [...passengerDetails];
    details[index] = { ...details[index], [name]: value };
    setPassengerDetails(details);
  };
 
  const nextPassenger = () => {
    if (currentPassenger < numPassengers - 1) {
      setCurrentPassenger(currentPassenger + 1);
    }
  };
 
  const previousPassenger = () => {
    if (currentPassenger > 0) {
      setCurrentPassenger(currentPassenger - 1);
    }
  };
 
  const handleSubmitForm = (event) => {
    event.preventDefault();
    event.stopPropagation();
 
    const form = event.currentTarget;
    if (form.checkValidity() === false) {
      setValidated(true);
      return;
    }
 
    setShowConfirmationPopUp(true);
    setValidated(true);

  };
 
  const handleCancelBooking = () => {
    bookingContextObject.setFlightObject(null);
    bookingContextObject.setPassengerObjects([]);
    viewBookingContextObject.setviewPassenger(false);
    viewBookingContextObject.setviewFlight(true);
    // navigateHook('/view-flights');
  };
 
  const handleNavigateToBookings = async () => {
    let savedPassengerList = [];
    console.log("In handleNavigateToBookings ");
 
    try {
      const passengerPromises = passengerDetails.map((passenger) =>
        postPassengerDetails(passenger)
      );
      savedPassengerList = await Promise.all(passengerPromises);
 
      console.log("Passengers saved:", savedPassengerList);
      bookingContextObject.setPassengerObjects(savedPassengerList);
 
      const flightId = bookingContextObject.flightObject.flightId;
      let passengerIdList = [];
      for (const passenger of savedPassengerList) {
        passengerIdList.push(passenger.passengerId);
      }
 
      console.log("In booking Layout", flightId, passengerIdList);
 
      // create booking
      const bookingObj = await createBooking(flightId, passengerIdList);
      console.log(bookingObj);
      bookingContextObject.setBookingObject(bookingObj);
 
      // navigate to book seats
      viewBookingContextObject.setviewPassenger(false);
      viewBookingContextObject.setviewBooking(true)
      
      //navigateHook('/select-seats');
    } catch (error) {
      console.error("Error saving passengers:", error);
    }
  };
  const [currentDate] = useState(new Date().toISOString().split('T')[0]);
 
  return (
    <Container>
      <Modal
        show={showConfirmationPopUp}
        onHide={() => setShowConfirmationPopUp(false)}
      >
        <Modal.Header closeButton>
          <Modal.Title>Confirm Booking</Modal.Title>
        </Modal.Header>
        <Modal.Body>Are you sure you want to confirm your booking?</Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleCancelBooking}>
            Cancel
          </Button>
          <Button variant="outline-secondary" onClick={handleNavigateToBookings}>
            Save Changes
          </Button>
        </Modal.Footer>
      </Modal>
 
      <Row className="my-4">
        <Col>
          <Form.Group controlId="numPassengers">
            <Form.Label>Number of Passengers</Form.Label>
            <Form.Control
              id="numberPassenger"
              type="number"
              value={numPassengers}
              onChange={handleNumPassengersChange}
              min="1"
              required
            />
            <Form.Control.Feedback className="supportText" type="invalid">
              Please enter the number of passengers.
            </Form.Control.Feedback>
          </Form.Group>
        </Col>
      </Row>
 
      {numPassengers > 0 && (
        <Row className="my-4 ">
          <Col className="card mx-3 p-5">
            <h4>Passenger {currentPassenger + 1} Details</h4>
            <Form noValidate validated={validated} onSubmit={handleSubmitForm}>
              <Form.Group controlId={`first-name-${currentPassenger}`}>
                {/* <Form.Label>First Name</Form.Label> */}
                <Form.Control
                  className="mb-2"
                  type="text"
                  name="firstName"
                  placeholder="First Name"
                  value={passengerDetails[currentPassenger]?.firstName || ""}
                  onChange={(e) => handleChange(e, currentPassenger)}
                  minLength={3}
                  required
                />
                <Form.Control.Feedback className="supportText" type="invalid">
                  First name is required & of minimum 3 characters in length.
                </Form.Control.Feedback>
              </Form.Group>
 
              <Form.Group controlId={`last-name-${currentPassenger}`}>
                {/* <Form.Label>Last Name</Form.Label> */}
                <Form.Control
                  className="mb-2"
                  type="text"
                  name="lastName"
                  placeholder="Last Name"
                  value={passengerDetails[currentPassenger]?.lastName || ""}
                  onChange={(e) => handleChange(e, currentPassenger)}
                  minLength={3}
                  required
                />
                <Form.Control.Feedback className="supportText" type="invalid">
                  Last name is required & of minimum 3 characters in length.
                </Form.Control.Feedback>
              </Form.Group>
 
              <Form.Group controlId={`emailId-${currentPassenger}`}>
                {/* <Form.Label>Email Id</Form.Label> */}
                <Form.Control
                  className="mb-2"
                  type="email"
                  name="emailId"
                  placeholder="Email Id"
                  value={passengerDetails[currentPassenger]?.emailId || ""}
                  onChange={(e) => handleChange(e, currentPassenger)}
                  required
                  pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$"
                />
                <Form.Control.Feedback className="supportText" type="invalid">
                  Please enter a valid email address.
                </Form.Control.Feedback>
              </Form.Group>
 
              <Form.Group controlId={`phoneNumber-${currentPassenger}`}>
                {/* <Form.Label>Phone Number</Form.Label> */}
                <Form.Control
                  className="mb-2"
                  type="text"
                  name="phoneNumber"
                  placeholder="Phone Number"
                  value={passengerDetails[currentPassenger]?.phoneNumber || ""}
                  onChange={(e) => handleChange(e, currentPassenger)}
                  required
                  pattern="\d{10}"
                />
                <Form.Control.Feedback className="supportText" type="invalid">
                  Phone number must be 10 digits.
                </Form.Control.Feedback>
              </Form.Group>
 
              <Form.Group controlId={`dateOfBirth-${currentPassenger}`}>
                {/* <Form.Label>Date of Birth</Form.Label> */}
                <Form.Control
                  className="mb-2"
                  type="date"
                  placeholder="Enter Date of Birth"
                  name="dateOfBirth"
                  value={passengerDetails[currentPassenger]?.dateOfBirth || ""}
                  onChange={(e) => handleChange(e, currentPassenger)}
                  max={currentDate}
                  required
                />
                <Form.Control.Feedback className="supportText" type="invalid">
                  Date of birth is required.
                </Form.Control.Feedback>
              </Form.Group>
 
              <Form.Group controlId={`nationality-${currentPassenger}`}>
                {/* <Form.Label>Nationality</Form.Label> */}
                <Form.Control
                  className="mb-2"
                  type="text"
                  name="nationality"
                  placeholder="Nationality"
                  value={passengerDetails[currentPassenger]?.nationality || ""}
                  onChange={(e) => handleChange(e, currentPassenger)}
                  required
                />
                <Form.Control.Feedback type="invalid">
                  Nationality is required.
                </Form.Control.Feedback>
              </Form.Group>
 
              <Form.Group controlId={`passportIdNumber-${currentPassenger}`}>
                {/* <Form.Label>Passport Id Number</Form.Label> */}
                <Form.Control
                  className="mb-2"
                  type="text"
                  name="passportIdNumber"
                  placeholder="Passport Id Number"
                  value={
                    passengerDetails[currentPassenger]?.passportIdNumber || ""
                  }
                  onChange={(e) => handleChange(e, currentPassenger)}
                  pattern="[a-zA-Z0-9]{6,9}"
                  required
                />
                <Form.Control.Feedback type="invalid">
                  Passport Id Number is required & it should be of 6 - 9 characters only.
                </Form.Control.Feedback>
              </Form.Group>
 
              <Form.Group controlId={`gender-${currentPassenger}`}>
                {/* <Form.Label>Gender</Form.Label> */}
                <Form.Control
                  className="mb-2"
                  as="select"
                  name="gender"
                  placeholder="Gender"
                  value={passengerDetails[currentPassenger]?.gender || ""}
                  onChange={(e) => handleChange(e, currentPassenger)}
                  required
                >
                  <option value="">Select Gender</option>
                  <option value="Male">Male</option>
                  <option value="Female">Female</option>
                  <option value="Others">Other</option>
                </Form.Control>
                <Form.Control.Feedback type="invalid">
                  Gender is required.
                </Form.Control.Feedback>
              </Form.Group>
 
              
 
              <div className="d-flex">
                <Button
                  variant="secondary"
                  onClick={previousPassenger}
                  disabled={currentPassenger === 0} className="me-1"
                >
                  Previous
                </Button>
                <Button
                  variant="secondary"
                  onClick={nextPassenger}
                  disabled={currentPassenger === numPassengers - 1} className="mx-1"
                >
                  Next
                </Button>
                {currentPassenger == numPassengers - 1 ? (
                  <Button type="submit" className="ms-auto btn-outline-secondary">Submit Details</Button>
                ) : (
                  ""
                )}
              </div>
            </Form>
          </Col>
        </Row>
      )}
    </Container>
  );
};
 
export default PassengerForm;