import React, { useState, useEffect, useContext } from "react";
import {
  BrowserRouter as Router,
  Routes,
  Route,
  useNavigate,
  Navigate,
} from "react-router-dom";
import { ToastContainer } from "react-toastify";

//CSS import
import "bootstrap/dist/css/bootstrap.min.css";
import "react-toastify/dist/ReactToastify.css";

// Component Import
import Login from "./Login";
import Register from "./Register";
import FlightForm from "./AdminFlightComponents/FlightForm";
import ViewAdmin from "./AdminFlightComponents/ViewAdmin";
import UpdateFlight from "./AdminFlightComponents/UpdateFlight";
import Eticket from "./Eticket";
import BookingLayout from "./BookingsComponents/BookingLayout";
import PassengerForm from "./PassngersComponents/PassengerForm";
import FlightSeats from "./SeatSelectionComponents/FlightSeats";
import { BookingContextProvider } from "../Context/BookingContext";
import { ViewBookingContextProvider } from "../Context/ViewBookingContext";
import ViewUser from "./ViewUser";
import NavBar from "./NavBar";
import Logout from "./Logout";
import Home from "./Home";
import { AuthContextProvider } from "../Context/AuthContext";
import UnauthorizedPage from "./UnauthorizedPage";
import ProtectedRoute from "./ProtectedRoute";
import { jwtDecode } from "jwt-decode";

export const updateContext = React.createContext();
export const flightContext = React.createContext();

function App() {
  const [updateFlight, setUpdateFlight] = useState(false);
  const [flightId, setFlightId] = useState(0);
 
  
  return (
    <div className="App">
      <ToastContainer />
        <AuthContextProvider>
          <NavBar />
          <BookingContextProvider>
            <ViewBookingContextProvider>
              <Routes>
                {/* <Route exact path="/" element={<Home />}></Route> */}
                <Route exact path="/" element={<Login />}></Route>
                <Route path="/register" element={<Register />}></Route>

                <Route
                  path="/admin"
                  element={
                    <ProtectedRoute allowedRole={"ADMIN"}>
                      <flightContext.Provider value={[flightId, setFlightId]}>
                        <updateContext.Provider
                          value={[updateFlight, setUpdateFlight]}
                        >
                          <ViewAdmin />
                        </updateContext.Provider>
                      </flightContext.Provider>
                    </ProtectedRoute>
                  }
                ></Route>

                <Route
                  path="/view-user"
                  element={
                    <ProtectedRoute allowedRole={"USER"}>
                      <ViewUser />
                    </ProtectedRoute>
                  }
                />
                <Route path="/home" element={<Home/>}></Route>
                <Route path="/addflight" element={<FlightForm />}></Route>
                <Route path="/updateFlight" element={<UpdateFlight />}></Route>
                <Route path="/passenger-details" element={<PassengerForm />} />
                <Route path="/select-seats" element={<FlightSeats />} />
                <Route path="/view-bookings" element={<BookingLayout />} />
                <Route path="/view-eticket" element={<Eticket />}></Route>
                <Route path="/logout" element={<Logout />}></Route>
                <Route
                  path="/unauthorized"
                  element={<UnauthorizedPage />}
                ></Route>
              </Routes>
            </ViewBookingContextProvider>
          </BookingContextProvider>
        </AuthContextProvider>
    </div>
  );
}

export default App;
