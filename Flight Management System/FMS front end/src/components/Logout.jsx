import { useContext } from "react";
import { AuthContext } from "../Context/AuthContext";
import {   Navigate } from "react-router-dom";
const Logout = () =>{
   
    const authContext = useContext(AuthContext);
    authContext.setIsLoggedIn(false); 
    authContext.setRole('')
    window.sessionStorage.clear()
    window.sessionStorage.setItem("token",null);
    window.sessionStorage.setItem("isLoggedIn",false);
    return <Navigate to="/" />
   
    
    
}

export default Logout;