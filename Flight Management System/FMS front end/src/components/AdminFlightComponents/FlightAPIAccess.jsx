import axios from "axios";
import { toast } from "react-toastify";

const flightURL = 'http://localhost:8086/api/flight';
let adminToken = window.sessionStorage.getItem("token");

const notifysuccess = (str) => {
    toast.success(str);
};

const notifyerror = (str) => {
    toast.error(str);
};



const deleteFlight = async (id)=>{
    adminToken = window.sessionStorage.getItem("token");
    console.log("Deleting flight "+id);
    let flag = false;
    await axios.delete(flightURL+"/"+id,
        {
        headers :{ Authorization : adminToken}
    })
    .then(() => {
        notifysuccess("Flight Deleted Successfully.");
        flag = true;
    })
    .catch((error)=>{
        console.log(error);
        flag = false
        notifyerror("Error occur while deleting flight.")
    });
    return flag
}



const getAllFlights = async ({ departureValue='', destinationValue='', dateValue='', maxPriceValue='' } = {}) =>{
    
    adminToken = window.sessionStorage.getItem("token");
    const paramsObj = {};
    if(departureValue !== ''){
        paramsObj.departure = departureValue;
        console.log("Depature", departureValue);
    }
    if(destinationValue !== ''){
        paramsObj.destination = destinationValue
        console.log("Destination", destinationValue);
    }

    if(dateValue !== ''){
        console.log(typeof(dateValue))
        paramsObj.departureDateAndTime = dateValue
        console.log("date value", dateValue);
    }

    if(maxPriceValue !== ''){
        paramsObj.priceMax = maxPriceValue
        console.log("Price upper limit", maxPriceValue);
    }

    try{ 
        adminToken = window.sessionStorage.getItem("token");
        const response = await axios.get(flightURL, {
            params: paramsObj,
            headers: {
                'Authorization': adminToken
            }              
        })
        if(response.status === 200){
            // console.log("Log for data got is : ", response.data.data);
            return response.data.data;
        }
        else{
            console.log("something happened as status code is not 200")
        }
    }
    catch(error){
        console.error("Error occured ", error)
    }
}
export { getAllFlights , deleteFlight};