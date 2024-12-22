import { useState,useContext } from "react";
import axios from "axios";
import useRazorpay from "react-razorpay";
import { toast } from "react-toastify";
import { useNavigate } from 'react-router-dom';
import {ViewBookingContext} from "../../Context/ViewBookingContext"
import { BookingContext } from "../../Context/BookingContext";
import {updateSeatsToBooking } from "../SeatSelectionComponents/SeatsApiAccess";

const usePayment = () => {
  const [Razorpay] = useRazorpay();
  const [paymentStatus, setPaymentStatus] = useState(null);
  const navigateHook = useNavigate();
  const viewBookingContextObject = useContext(ViewBookingContext);
  const bookingContextObject = useContext(BookingContext);
  const notifyInformation = (noOfPassenger, seatSelected) => {
    toast.info(
      `You are required to select ${noOfPassenger} seats, You have selected ${seatSelected} seats!!`
    );
  };

  const columns = 6;

  const notifyError = () => {
    toast.error("Error while doing payment.");
  };

  const notifySeatSuccess = () => {
    toast.success("Booking Confirmed !!");
  };
  const notifySeatError = () => {
    toast.success("Something went wrong!!");
  };

  function indexToSeatObject(row, col, isSelected) {
    let seatObj = {};
    // extracting seat Column from col
    seatObj.seatNumber = col;

    // extracting row number from row

    seatObj.seatRowNumber = row;

    // extracting class type
    if (row < 4) seatObj.seatClassType = "First";
    else if (row < 8) seatObj.seatClassType = "Business";
    else seatObj.seatClassType = "Economy";

    // extracting seat is already occupied or not
    if (isSelected) seatObj.isOccupied = true;
    else seatObj.isOccupied = false;

    // extracting seat type
    if (col === 0 || col === columns - 1) {
      seatObj.seatType = "Window";
    } else if (col === 1 || col === columns - 2) {
      seatObj.seatType = "Middle";
    } else {
      seatObj.seatType = "Aisle";
    }
    return seatObj;
  }



  const paymentDone = async (orderId, bookingId,seatObjectlist) => {
    try {
      const updateOrderResponse = await axios.get(
        `/api/payment/updateOrder/${orderId}`,
        {
          headers: {
            Authorization: window.sessionStorage.getItem("token"),
          },
        }
      );
      console.log(updateOrderResponse);
      const seatBooked = updateSeatsToBooking( seatObjectlist,bookingContextObject.bookingObject.bookingId);
      console.log(seatBooked,"Seat booked Successfully")
      // api call
      if (seatBooked) {
        notifySeatSuccess();
        viewBookingContextObject.setviewSeat(false);
        viewBookingContextObject.setviewFlight(true);
      }else{
        notifySeatError();
      }

      try{
        const createEticketResponse = await axios.get(
        `/api/eticket/createEticket/${bookingId}`,
        {
          headers: {
            Authorization: window.sessionStorage.getItem("token"),
          },
        }
      )
      console.log(createEticketResponse,"Eticket");
    }
      catch(error){
        console.log(error)
      }
      
      setPaymentStatus("success");
      viewBookingContextObject.setviewBooking(false);
      viewBookingContextObject.setviewFlight(true);
      navigateHook('/view-user')
    } catch (error) {
      console.error("Error updating order or creating eticket:", error);
      notifyError();
      setPaymentStatus("error");
    }
  };

  const handlePayment = async (bookingId,selectedSeats) => {
    let seatObjectlist = [];

    for (let i = 0; i < 12; i++) {
      for (let j = 0; j < 6; j++) {
        if (selectedSeats[i][j] === true) {
          seatObjectlist.push(indexToSeatObject(i, j, false));
        }
      }
    }
    console.log("here")

    if (
      seatObjectlist.length > bookingContextObject.passengerObjects.length ||
      seatObjectlist.length < bookingContextObject.passengerObjects.length
    ) {
      notifyInformation(
        bookingContextObject.passengerObjects.length,
        seatObjectlist.length
      );
      return;
    }
    try {
      console.log(seatObjectlist)
      const createOrderResponse = await axios.post(
        `/api/payment/createOrder/${bookingId}`,seatObjectlist,
        {
          headers: {
            Authorization: window.sessionStorage.getItem("token"),
          },
        }
      );
      console.log(createOrderResponse.data);

      const options = {
        key: "rzp_test_LrHTWqr4yuxxA2",
        amount: createOrderResponse.data.amount,
        currency: "INR",
        name: "FMS Corp",
        description: "Test Transaction",
        image: "../../Assets/logo.svg",
        order_id: createOrderResponse.data.paymentId,
        handler: (response) => {
          paymentDone(response.razorpay_order_id, bookingId,seatObjectlist);
        },
        
        notes: {
          address: "Razorpay Corporate Office",
        },
        theme: {
          color: "#53a20e",
        },
      };
      const rzp1 = new Razorpay(options);

      rzp1.on("payment.failed", function (response) {
        alert(response.error.reason);
      });

      rzp1.open();
    } catch (error) {
      console.error("Error creating order:", error);
      notifyError();
      setPaymentStatus("error");
    }
  };

  return {
    handlePayment,
    paymentStatus,
  };
};

export default usePayment;
