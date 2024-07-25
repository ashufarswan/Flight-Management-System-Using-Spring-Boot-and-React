import axios from "axios";



const getAllSeats = async (flightId) =>{
    
   
    const getUrl = 'http://localhost:8086/api/booking/getAllSeats/flight/' + flightId;
    
    try{
        const response = await axios.get(getUrl, {
            headers :{
                'Authorization' : window.sessionStorage.getItem("token")
            }
        })
        if(response.status == 200){
            console.log("Connection successfull status 200 log");
            return response.data.data
        } else{
            console.log("Connection successfull but something went wrong");
        }
        
    }
    catch(error){
        console.error('error', error)
        return []
    }
}

async function updateSeatsToBooking(seatList, bookingId){
    console.log("inside update seats api access ", seatList, bookingId);
    const tokenString = window.sessionStorage.getItem("token")
    const updateSeatsUrl = `http://localhost:8086/api/booking/updateSeat/${bookingId}` 
    
    try{
        const response = await axios.put(updateSeatsUrl, 
            seatList,{
                headers :{
                    'Authorization' : tokenString
                }
            }
                )
            if(response.status == 200){
                console.log(response.data)
                return true;
                //window.alert('Seats updated successfully')
            }
            else{
                return false;
            }

    } catch(error){
        console.error('error', error);
        console.log("Errororororororororo")
        return false;
    }
 } 

export {getAllSeats, updateSeatsToBooking};