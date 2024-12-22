import axios from "axios";



const getAllFlights = async ({ departureValue='', destinationValue='', dateValue='', maxPriceValue='' } = {}) =>{
    const flightURL = 'http://localhost:8086/api/flight';
    const adminToken = window.sessionStorage.getItem("token")
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

    console.log(paramsObj)

    try{ 
        const response = await axios.get(flightURL, {
            params: paramsObj,
            headers: {
                'Authorization': adminToken
            }              
        })
        if(response.status === 200){
            console.log("Log for data got is : ", response.data.data);
             // filter flights according to Current date, Flights of Current Date and future dates are displayed
             const filteredResponse = response.data.data.filter( flightObj => {
                const flightDate = new Date(flightObj.departureDateAndTime)
                const currentDate = new Date()
                if(flightDate >= currentDate)
                    return true;
                return false
            })
            return filteredResponse;
        }
        else{
            console.log("something happened as status code is not 200")
        }
    }
    catch(error){
        console.error("Error occured ", error)
        return [];
    }
}
export default getAllFlights;