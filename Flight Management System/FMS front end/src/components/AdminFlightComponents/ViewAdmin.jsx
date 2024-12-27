import React, { useContext, useEffect, useState } from "react";
import {
  Container,
  Row,
  Col,
  Navbar,
  Nav,
  NavItem,
  NavLink,
} from "react-bootstrap";
import "../../css/SideBar.css";
import AdminFlightLayout from "./AdminFlightLayout";
import FlightForm from "./FlightForm";
import UpdateFlight from "./UpdateFlight";
import { updateContext } from "../App";
import { getAllFlights } from "./FlightAPIAccess";
import { jwtDecode } from "jwt-decode";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "../../Context/AuthContext";
import { toast } from "react-toastify";

const ViewAdmin = () => {
  const [flights, setFlights] = useState([]);
  const [viewFlight, setViewFlight] = useState(true);
  const [addFlight, setAddFlight] = useState(false);
  const [updateFlight, setUpdateFlight] = useContext(updateContext);
  const authContext = useContext(AuthContext);
  const navigate = useNavigate();

  const notifyError = () => {
    toast.error("Unauthorized Access");
  };

  useEffect(() => {
    if (window.sessionStorage.getItem("token") === "null") {
      navigate("/");
    } else if (
      jwtDecode(window.sessionStorage.getItem("token")).role === "USER"
    ) {
      //console.log("Invalid Access");
      notifyError();
      navigate("/");
    }
  }, [authContext.isLoggedIn, navigate]);

  ////console.log(updateFlight);

  const handleClick = (event) => {
    event.preventDefault();
    if (event.target.id === "viewflight") {
      setViewFlight(true);
      setAddFlight(false);
      setUpdateFlight(false);
    } else if (event.target.id === "addflight") {
      setViewFlight(false);
      setAddFlight(true);
    }
  };
  useEffect(() => {
    getAllFlights().then((data) => {
      setFlights(data);
    });
  }, []);

  // //console.log(flights[0])
  return (
    <Container
      className="bg-white"
      fluid
      style={{ height: "87vh", position: "relative", top: "0px" }}
    >
      <Row>
        <Col xs={2} md={2} lg={2} className="bg-dark side h-100">
          <Navbar variant="dark" expand={false}>
            <Nav className="flex-column">
              <NavItem>
                <NavLink id="viewflight" onClick={handleClick}>
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    width="15"
                    height="15"
                    viewBox="0 0 24 24"
                    fill="none"
                    stroke="currentColor"
                    strokeWidth="2"
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    className="feather feather-eye"
                  >
                    <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"></path>
                    <circle cx="12" cy="12" r="3"></circle>
                  </svg>
                  View Flight
                </NavLink>
              </NavItem>
              <NavItem>
                <NavLink id="addflight" onClick={handleClick}>
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    width="15"
                    height="15"
                    viewBox="0 0 24 24"
                    fill="none"
                    stroke="currentColor"
                    strokeWidth="2"
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    className="feather feather-plus-square"
                  >
                    <rect
                      x="3"
                      y="3"
                      width="18"
                      height="18"
                      rx="2"
                      ry="2"
                    ></rect>
                    <line x1="12" y1="8" x2="12" y2="16"></line>
                    <line x1="8" y1="12" x2="16" y2="12"></line>
                  </svg>
                  Add Flight
                </NavLink>
              </NavItem>
              <NavItem>
                <NavLink href="/logout">
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    width="15"
                    height="15"
                    viewBox="0 0 24 24"
                    fill="none"
                    stroke="currentColor"
                    strokeWidth="2"
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    className="feather feather-log-out"
                  >
                    <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"></path>
                    <polyline points="16 17 21 12 16 7"></polyline>
                    <line x1="21" y1="12" x2="9" y2="12"></line>
                  </svg>
                  Logout
                </NavLink>
              </NavItem>
            </Nav>
          </Navbar>
        </Col>
      {/* Main Content */}
        <Col
          xs={10}
          md={10}
          lg={10}
          className="main-content"
          style={{ "overflowY": "scroll" }}
        >
          {viewFlight && (
            <div>
              {!updateFlight ? (
                <AdminFlightLayout />
              ) : (
                <UpdateFlight data={flights} />
              )}
            </div>
          )}
          {addFlight && (
            <div>
              <FlightForm setFlights = {setFlights}/>
            </div>
          )}
        </Col>
      </Row>
    </Container>
   
  );
};

export default ViewAdmin;
