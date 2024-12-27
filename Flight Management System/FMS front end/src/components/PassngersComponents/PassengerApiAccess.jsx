import axios from "axios"
import { jwtDecode } from "jwt-decode";



const postPassengerDetails = async(passenger) =>{

    const postUrl = 'http://localhost:8086/api/passenger'
    const adminToken = window.sessionStorage.getItem("token")


    try{
       const response =  await axios.post(postUrl, passenger,{
            headers :{
                'Authorization' : adminToken
            },
        })
        
            if(response.status === 201){
                //console.log(response.data.data, "Data sent successfull", typeof(response))
                // what to return
                return response.data.data;
            }
            else{
                //console.log(`status with ${response}`);
                return response;
            }

        } 
    catch(error){
        console.error(error);
    }
}

const createBooking = async (flightId, passengerIdList) =>{
    
    const adminToken = window.sessionStorage.getItem("token")
    const userId = jwtDecode(adminToken).id;
    let createBookingUrl = `http://localhost:8086/api/booking/flight/`;


    const queryParams = passengerIdList.map(id => `passenger=${id}`).join('&');
    
    const payload = {
            "numberOfPassengers": passengerIdList.length,
            "userId" : userId,
            "bookingDateAndTime": formatDateNow(),
            "bookingStatus": "created"
        }
    
    try{
        const response = await axios.post(createBookingUrl + flightId + `?${queryParams}`, payload , {
            headers: {
            'Authorization': adminToken,
          },
        }  
        )

       //console.log("booking created **** ", response, typeof(response), response.data, typeof(response.data));
       return response.data.data;
       

    } catch(error){
        console.error(error)
    }
}

function formatDateNow() {
    const now = new Date();
    
    const year = now.getFullYear();
    const month = String(now.getMonth() + 1).padStart(2, '0'); // Months are zero-based
    const day = String(now.getDate()).padStart(2, '0');
    const hours = String(now.getHours()).padStart(2, '0');
    const minutes = String(now.getMinutes()).padStart(2, '0');
    const seconds = String(now.getSeconds()).padStart(2, '0');
    const milliseconds = String(now.getMilliseconds()).padStart(3, '0');
    
    // Get the timezone offset in minutes and convert it to hours and minutes
    const timezoneOffset = -now.getTimezoneOffset();
    const timezoneHours = String(Math.floor(timezoneOffset / 60)).padStart(2, '0');
    const timezoneMinutes = String(timezoneOffset % 60).padStart(2, '0');
    const timezoneSign = timezoneOffset >= 0 ? '+' : '-';
    
    return `${year}-${month}-${day}T${hours}:${minutes}:${seconds}.${milliseconds}${timezoneSign}${timezoneHours}:${timezoneMinutes}`;
  }

export {postPassengerDetails, createBooking};