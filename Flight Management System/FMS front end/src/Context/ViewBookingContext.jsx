import React from 'react';
import { createContext, useState } from "react";

export const ViewBookingContext = createContext(null);

export const ViewBookingContextProvider = (props) =>{
    const [viewBooking,setviewBooking] = useState(false);
    const [viewFlight,setviewFlight] = useState(true);
    const [viewSeatModal,setviewSeatModal] = useState(false);
    return(
    <ViewBookingContext.Provider value={{viewBooking,setviewBooking,viewFlight,
    setviewFlight,viewSeatModal,setviewSeatModal}}>
        {props.children}
    </ViewBookingContext.Provider>
    )
}