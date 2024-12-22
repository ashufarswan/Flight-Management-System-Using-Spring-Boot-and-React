import React from 'react';
import { createContext, useState } from "react";

export const ViewBookingContext = createContext(null);

export const ViewBookingContextProvider = (props) =>{
    const [viewBooking,setviewBooking] = useState(false);
    const [viewFlight,setviewFlight] = useState(true);
    const [viewPassenger,setviewPassenger] = useState(false);
    const [viewSeat,setviewSeat] = useState(false);
    return(
    <ViewBookingContext.Provider value={{viewBooking,setviewBooking,viewFlight,
    setviewFlight,viewPassenger,setviewPassenger,viewSeat,setviewSeat}}>
        {props.children}
    </ViewBookingContext.Provider>
    )
}