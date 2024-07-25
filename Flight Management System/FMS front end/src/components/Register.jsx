import React, { useState } from "react";
import axios from "axios";
import { FaUser, FaLock, FaEnvelope, FaPhoneAlt } from "react-icons/fa";
import "../css/LoginRegister.css";
import { Link } from "react-router-dom";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

const Register = () => {
  const [userName, setUserName] = useState("");
  const [email, setEmail] = useState("");
  const [phone, setPhone] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [phoneError, setPhoneError] = useState("");

  const notifySuccess = () => {
    toast.success("User Registered Successfully !!!");
  };
  const notifyError = (str) => {
    toast.error(str);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (password === confirmPassword) {
      console.log(userName);
      await axios
        .post("/api/user/register", {
          name: userName,
          email: email,
          password: password,
          phone: phone,
        })
        .then((response) => {
          console.log(response.data);
          notifySuccess();
        })
        .catch((error) => {
          console.error(error.response.data);
          notifyError(error.response.data.message);
        });
    }
  };

  return (
    <div className="outerwrapper">
      <div className="wrapper">
        <div className="form-box register animate__animated animate__fadeIn">
          <form onSubmit={handleSubmit}>
            <h1>Registration</h1>
            <div className="input-box">
              <input
                type="text"
                placeholder="Username"
                required
                id="username"
                value={userName}
                onChange={(e) => setUserName(e.target.value)}
              />
              <FaUser className="icon" />
            </div>
            <div className="input-box">
              <input
                type="email"
                placeholder="Email"
                required
                id="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
              />
              <FaEnvelope className="icon" />
            </div>
            <div className="input-box">
              <input
                type="phone"
                placeholder="Phone Number"
                required
                id="phonenumber"
                value={phone}
                onChange={(e) => setPhone(e.target.value)}
              />
              <FaPhoneAlt className="icon" />
            </div>
            <span
              className="text-danger text-uppercase fw-bold"
              style={{
                position: "relative",
                left: "78px",
                top: "-10px",
                fontSize: "12px",
              }}
            >
              {/^$|^\d{10}$/.test(phone) ? (
                ""
              ) : (
                <small >Phone no must be 10 digit number</small>
              )}
            </span>
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
            <div className="input-box">
              <input
                type="password"
                placeholder="Confirm Password"
                required
                id="confirmPassword"
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
              />
              <FaLock className="icon" />
            </div>
            <span
              className="text-danger text-uppercase fw-bold"
              style={{
                position: "relative",
                left: "78px",
                top: "-10px",
                fontSize: "12px",
              }}
            >
              <small>
                {password === confirmPassword ? "" : "Passwords don't match"}
              </small>
            </span>
            <button type="submit">Register</button>
            <div className="register-link">
              <p>
                Already have an account? <Link to="/">Login</Link>
              </p>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};
export default Register;
