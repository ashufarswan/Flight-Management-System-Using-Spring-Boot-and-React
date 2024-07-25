import React, { useState, useEffect, useContext } from "react";
import axios from "axios";
import { FaUser, FaLock } from "react-icons/fa";
import { Link, Navigate, useNavigate } from "react-router-dom";
import "../css/LoginRegister.css";
import { jwtDecode } from "jwt-decode";
import { toast } from "react-toastify";
import { AuthContext } from "../Context/AuthContext";

const Login = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();
  const authContext = useContext(AuthContext);


  if(sessionStorage.getItem('isLoggedIn') === "true"){
  try{
    if(jwtDecode(sessionStorage.getItem("token")).role === 'ADMIN')
      return <Navigate to='/admin' />
    else 
      return <Navigate to='/view-user' />
      } catch(e){
        return <Navigate to='/' />
      }
  }
  else{
    localStorage.setItem('passengerObjects', null);
    localStorage.setItem('rzp_device_id', null);
    localStorage.setItem('role', '');
    localStorage.setItem('rzp_checkout_user_id', null);
    localStorage.setItem('bookingObject', null);
    localStorage.setItem('isLoggedIn', false);
    localStorage.setItem('rzp_checkout_anon_id', null);
    localStorage.setItem('flightObject', null);}
  

  const notifyerror = () => {
    toast.error("Invalid Username or Password");
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    //console.log(password);
    await axios
      .post("/api/user/login", {
        userName: username,
        password: password,
      })
      .then((response) => {
        const decoded = jwtDecode(response.data);
        window.sessionStorage.setItem("token", response.data);
        window.sessionStorage.setItem("isLoggedIn", true);
        authContext.setIsLoggedIn(true);
        authContext.setRole(decoded.role);
        console.log(decoded.role);
        if (decoded.role === "ADMIN") {
          console.log("Logging in as admin");
          navigate("/admin");
        } else {
          console.log("Logging in as user");
          navigate("/view-user");
        }
      })
      .catch((error) => {
        console.error(error.response.data);
        notifyerror();
      });
  };

  return (
    <div className="outerwrapper ">
      <div className="wrapper">
        <div className="form-box login animate__animated animate__fadeIn">
          <form onSubmit={handleSubmit}>
            <h1>Login</h1>
            <div className="input-box">
              <input
                type="text"
                placeholder="Username"
                required
                id="username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
              />
              <FaUser className="icon" />
            </div>
            <div className="input-box">
              <input
                type="password"
                placeholder="Password"
                required
                id="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
              <FaLock className="icon" />
            </div>
            <button type="submit">Login</button>
            <div className="register-link">
              <p>
                Don't have an account?<Link to="/register">Register</Link>
              </p>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};

export default Login;
