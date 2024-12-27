import {jwtDecode} from 'jwt-decode'
import axios from 'axios';


const getBookingsByUserId = async() =>{
    const tokenString = window.sessionStorage.getItem("token")
    const userId = jwtDecode(tokenString).id;
    const bookingsByUserIdUrl = `http://localhost:8086/api/booking/byUserId/${userId}`
    try{
        const response = await axios.get(bookingsByUserIdUrl, {
            headers :{
                'Authorization' : tokenString
            }
        })
        if(response.status === 200){
            //console.log("Bookings fetch by User id successfull")
            //console.log(response);
            return response.data.data;
        } 
        else{
            //console.log(`Connection made  but  unsuccessfull with status code ${response.status}`);
        }   
    }
    catch(error){
        console.error('error', error);
        return []
    }
}


function getUserName(){
    const tokenString = window.sessionStorage.getItem("token")
    return jwtDecode(tokenString).sub;
}


  
export  {getBookingsByUserId, getUserName};