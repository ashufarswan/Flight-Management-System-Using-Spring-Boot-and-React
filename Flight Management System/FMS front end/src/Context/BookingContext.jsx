import { createContext, useState, useEffect } from "react";

export const BookingContext = createContext(null);

export const BookingContextProvider = (props) =>{
    const [flightObject, setFlightObject] = useState(() => {
        const storedFlightObject = localStorage.getItem("flightObject");
        return storedFlightObject ? JSON.parse(storedFlightObject) : null;
      });
    
      const [passengerObjects, setPassengerObjects] = useState(() => {
        const storedPassengerObjects = localStorage.getItem("passengerObjects");
        return storedPassengerObjects ? JSON.parse(storedPassengerObjects) : [];
      });
    
      const [bookingObject, setBookingObject] = useState(() => {
        const storedBookingObject = localStorage.getItem("bookingObject");
        return storedBookingObject ? JSON.parse(storedBookingObject) : null;
      });
    
      // Update localStorage when state changes
      useEffect(() => {
        localStorage.setItem("flightObject", JSON.stringify(flightObject));
      }, [flightObject]);
    
      useEffect(() => {
        localStorage.setItem("passengerObjects", JSON.stringify(passengerObjects));
      }, [passengerObjects]);
    
      useEffect(() => {
        localStorage.setItem("bookingObject", JSON.stringify(bookingObject));
      }, [bookingObject]);
    
    return(
    <BookingContext.Provider value={{flightObject, setFlightObject, passengerObjects, setPassengerObjects, bookingObject, setBookingObject}}>
        {props.children}
    </BookingContext.Provider>
    )
}