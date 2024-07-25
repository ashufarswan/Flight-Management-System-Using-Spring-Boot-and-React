import React, { useEffect, useState, useContext } from "react";
import { Link, useNavigate, useLocation, Navigate } from "react-router-dom";
import { jwtDecode } from "jwt-decode";
import { AuthContext } from "../Context/AuthContext";

const NavBar = () => {
  const authContext = useContext(AuthContext);
  console.log(authContext.isLoggedIn, "Auth context");
  useEffect(() => {
    if (window.sessionStorage.getItem("token") === null) {
      console.log(authContext.isLoggedIn, " Nav Bar Mount Effect 1");
      window.sessionStorage.setItem("token", "null");
      window.sessionStorage.setItem("isLoggedIn", false);
    }
    const navbar = document.getElementById("navbarTop");
    if (!authContext.isLoggedIn) {
      navbar.classList.add("bg-none");
      navbar.classList.remove("bg-dark");
      navbar.classList.add("fixed-top");
    } else {
      navbar.classList.remove("bg-none");
      navbar.classList.remove("fixed-top");
      navbar.classList.add("bg-dark");
    }
  }, [authContext.isLoggedIn]);

  localStorage.setItem("passengerObjects", null);
  localStorage.setItem("rzp_device_id", null);
  localStorage.setItem("role", "");
  localStorage.setItem("rzp_checkout_user_id", null);
  localStorage.setItem("bookingObject", null);
  localStorage.setItem("isLoggedIn", false);
  localStorage.setItem("rzp_checkout_anon_id", null);
  localStorage.setItem("flightObject", null);

  let token = window.sessionStorage.getItem("token");

  const navigate = () => {
    console.log("here");
    try {
      return jwtDecode(window.sessionStorage.getItem("token")).role === "ADMIN"
        ? "/admin"
        : "/view-user";
    } catch (e) {
      return "/";
    }
  };

  const getUName = () => {
    if (!token) {
      return false;
    } else {
      return jwtDecode(token).sub.slice(0);
    }
  };

  return (
    <div>
      <nav
        id="navbarTop"
        className="navbar fixed-top navbar-expand-lg navbar-dark bg-none py-3"
      >
        <div style={{ marginLeft: "15px" }}>
          <Link className="navbar-brand">
            <svg
              version="1.1"
              id="Layer_1"
              xmlns="http://www.w3.org/2000/svg"
              viewBox="0 0 460 460"
            >
              <g>
                <path
                  style={{ fill: "#096C80" }}
                  d="M230,0C102.975,0,0,102.975,0,230c0,121.984,94.968,221.771,215,229.5L459.5,245
		c0.319-4.96,0.5-9.959,0.5-15C460,102.975,357.026,0,230,0z"
                />
                <path
                  style={{ fill: "#064855" }}
                  d="M459.5,245L240.37,25.5l-0.363,217.304l-183.04,2.729l144.814,144.814L190,434.5l25,25
		c4.961,0.32,9.959,0.5,15,0.5C351.984,460,451.771,365.032,459.5,245z"
                />
                <polygon
                  style={{ fill: "#2DB8D4" }}
                  points="240.37,25.5 190.37,25.5 197.556,55.501 218.774,102.337 230.37,192.5 280.37,202.5 
		330.37,192.5 	"
                />
                <polygon
                  style={{ fill: "#0C8FAA" }}
                  points="218.121,55.5 250.936,192.5 240.653,202.5 230.37,192.5 197.556,55.5 	"
                />
                <path
                  style={{ fill: "#89EBFF" }}
                  d="M200.37,192.5H105L85.868,157H35v35.5c0,41.421,33.579,75,75,75h90.37l10-37.5L200.37,192.5z"
                />
                <path
                  style={{ fill: "#4DE1FF" }}
                  d="M416.346,232.494l-15.368-20.005C387.598,200.083,369.685,192.5,350,192.5H200.37v75h30
		l-10.754,86.647l-22.06,50.353l-7.186,30h50l90-167H425C425,254.856,421.871,242.944,416.346,232.494z"
                />
                <rect
                  x="330.37"
                  y="212.5"
                  style={{ fill: "#C4F5FF" }}
                  width="20"
                  height="20"
                />
                <rect
                  x="290.37"
                  y="212.5"
                  style={{ fill: "#C4F5FF" }}
                  width="20"
                  height="20"
                />
                <rect
                  x="250.37"
                  y="212.5"
                  style={{ fill: "#C4F5FF" }}
                  width="20"
                  height="20"
                />
                <path
                  style={{ fill: "#C4F5FF" }}
                  d="M400.97,212.5h-30.6v20h45.962C412.358,224.985,407.15,218.229,400.97,212.5z"
                />
                <rect
                  x="210.37"
                  y="212.5"
                  style={{ fill: "#C4F5FF" }}
                  width="20"
                  height="20"
                />
                <rect
                  x="170.37"
                  y="212.5"
                  style={{ fill: "#FFFFFF" }}
                  width="20"
                  height="20"
                />
                <rect
                  x="130.37"
                  y="212.5"
                  style={{ fill: "#FFFFFF" }}
                  width="20"
                  height="20"
                />
                <polygon
                  style={{ fill: "#2DB8D4" }}
                  points="218.121,404.5 250.936,267.5 230.37,267.5 197.556,404.5 	"
                />
              </g>
            </svg>{" "}
            FMS
          </Link>
        </div>

        <div className="collapse navbar-collapse" id="navbarSupportedContent">
          <ul className="navbar-nav ms-auto">
            <li>
              {/* className="me-3" style={{ zIndex: 9999999990 }} */}
              <div className="nav-link me-3" style={{zIndex: 9999999990 }}>
                {!authContext.isLoggedIn ? (
                  <Link className="text-decoration-none" id="link" to="/">Login</Link>
                ) : (
                  <Link className="text-decoration-none" id="link" to={navigate()}>{getUName()}</Link>
                )}
              </div>
            </li>
          </ul>
        </div>
      </nav>
    </div>
  );
};
export default NavBar;
