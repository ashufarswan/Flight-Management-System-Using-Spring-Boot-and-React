import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import FlightCard from "./FlightCard";
import getAllFlights from "./FlightAPIAccess";
import FlightSearchBox from "./FlightSearchBox";
import "../../css/Flight.css";
import { BookingContextProvider } from "../../Context/BookingContext";

function FlightLayout (){ 
  const [flights, setFlights] = useState([]);
 

  const navigate = useNavigate();

  useEffect(() => {
    const token = sessionStorage.getItem('token');

    if (!token) {
      navigate('/');
    }
  }, [navigate]);


  
  useEffect(() => {
    //console.log("Render on Mount");
    getAllFlights().then((data) => {
      setFlights(data);
    });
  }, []);

  const getFliteredFlights = (argsObj) => {
    //console.log("Render on update");
    getAllFlights(argsObj).then((data) => {
      setFlights(data);
    });
  };

  return (
    <div className="layout pb-3 header-custom-box">
      <div className="mx-5 mb-3">
        <div className="card p-4 col-12 border-0">
            <h2 className="h4 lead ms-5 mb-4">Search Flights</h2>
          <FlightSearchBox getFliteredFlights={getFliteredFlights} />
        </div>
      </div>
      {/* <h6 className="lead ms-5">Search Results :</h6> */}
      <div className="mb-5">
        <div >
        {
            ////console.log("flights",flights)
            flights.length === 0 ?   <div className="alert alert-info alert-dismissible fade show" role="alert">
              <strong>No Flights Found !!</strong> You should check in on some of those fields above.
              <button type="button" className="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
            :
            flights.map((flight, index) => {
              return (
                <div key={index}>
                  <div className="card mx-5 border-0 shadow-0"> 
                    <BookingContextProvider> <FlightCard key={index} flightObj={flight}/> </BookingContextProvider>
                  </div>
                </div>
              );
            })
         
          }
        </div>
      </div>
    </div>
  );
}

export default FlightLayout;
