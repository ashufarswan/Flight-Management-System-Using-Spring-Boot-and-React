import React, { useEffect, useState } from "react";
import AdminFlightCard from "./AdminFlightCard";
import { getAllFlights } from "./FlightAPIAccess";
import AdminFlightSearchBox from "./AdminFlightSearchBox";
import "../../css/Flight.css";
import { useNavigate } from "react-router-dom";

function AdminFlightLayout() {
  const [flights, setFlights] = useState([]);
  const navigate = useNavigate();


  const updateFlights = (id) => {
    
    const updatedObjects = flights.filter((obj) => obj.flightId !== id);
   
    setFlights(updatedObjects);
  };

  useEffect(() => {
    console.log("Getting Flight Data");
    try{
    getAllFlights().then((data) => {
      setFlights(data);
    });
    }
    catch(error){
      navigate("/")
    }
  }, [navigate]);

  const getFliteredFlights = (argsObj) => {
    console.log("Render on update");
    getAllFlights(argsObj).then((data) => {
      setFlights(data);
    });
  };

  return (
    <div className="layout pb-4 header-custom-box">
      <div className="mx-5 mb-3">
        <div className="card p-4 col-12 border-0">
        <h2 className="h4 lead ms-5 mb-4">Search Flights</h2>
          <AdminFlightSearchBox getFliteredFlights={getFliteredFlights} />
        </div>
      </div>
      {/* <div className="heading res">Search Results :</div> */}
      <div>
        <div>
          {
             flights.length === 0 ?   <div class="alert alert-info alert-dismissible fade show" role="alert">
             <strong>No Flights Found !!</strong> You should check in on some of those fields above.
             <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
           </div>
           :
            //console.log("flights",flights)
            flights.map((flight, index) => {
              return (
                <div key={index}>
                  <div >
                    {" "}
                    <AdminFlightCard className="card mx-5 border-0 shadow-0"
                      
                      flightObj={flight}
                      updateFlights={updateFlights}
                    />
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

export default AdminFlightLayout;
