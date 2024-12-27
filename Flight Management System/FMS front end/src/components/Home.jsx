import React, { useState, useEffect, useContext } from "react";
import "../css/Home.css";
import flight1 from "../Assets/1.jpg";
import flight2 from "../Assets/2.jpg";
import flight3 from "../Assets/3.jpg";
import flight4 from "../Assets/4.jpg";
import flight5 from "../Assets/5.png";
import FlightLayout from "./FlightComponents/FlightLayout";
import ViewUser from "./ViewUser";
import { AuthContext } from "../Context/AuthContext";
import { BookingContextProvider } from "../Context/BookingContext";

const Home = () => {
  const authContext = useContext(AuthContext);

  return (
    <div className="landing-page">
      {/* Carousel Section */}
      <section className="carousel-section">
        <div
          id="carouselExampleIndicators"
          className="carousel slide"
          data-bs-ride="carousel"
        >
          <ol className="carousel-indicators">
            <li
              data-bs-target="#carouselExampleIndicators"
              data-bs-slide-to="0"
              className="active"
              aria-current="true"
              aria-label="Slide 1"
            ></li>
            <li
              data-bs-target="#carouselExampleIndicators"
              data-bs-slide-to="1"
              aria-label="Slide 2"
            ></li>
            <li
              data-bs-target="#carouselExampleIndicators"
              data-bs-slide-to="2"
              aria-label="Slide 3"
            ></li>
            <li
              data-bs-target="#carouselExampleIndicators"
              data-bs-slide-to="3"
              aria-label="Slide 4"
            ></li>
            <li
              data-bs-target="#carouselExampleIndicators"
              data-bs-slide-to="4"
              aria-label="Slide 5"
            ></li>
          </ol>
          <div className="carousel-inner">
            <div className="carousel-item active">
              <img className="d-block w-100" src={flight1} alt="First slide" />
            </div>
            <div className="carousel-item">
              <img className="d-block w-100" src={flight2} alt="Second slide" />
            </div>
            <div className="carousel-item">
              <img className="d-block w-100" src={flight3} alt="Third slide" />
            </div>
            <div className="carousel-item">
              <img className="d-block w-100" src={flight4} alt="Fourth slide" />
            </div>
            <div className="carousel-item">
              <img className="d-block w-100" src={flight5} alt="Fifth slide" />
            </div>
          </div>
          <button
            className="carousel-control-prev"
            type="button"
            data-bs-target="#carouselExampleIndicators"
            data-bs-slide="prev"
          >
            <span
              className="carousel-control-prev-icon"
              aria-hidden="true"
            ></span>
            <span className="visually-hidden">Previous</span>
          </button>
          <button
            className="carousel-control-next"
            type="button"
            data-bs-target="#carouselExampleIndicators"
            data-bs-slide="next"
          >
            <span
              className="carousel-control-next-icon"
              aria-hidden="true"
            ></span>
            <span className="visually-hidden">Next</span>
          </button>
        </div>
      </section>

      {/* Scrollable Flight Layout Section */}
      <section className="w-100">
        {authContext.isLoggedIn ? (
          <BookingContextProvider>
            {" "}
            <ViewUser />
          </BookingContextProvider>
        ) : (
          <FlightLayout />
        )}
      </section>
    </div>
  );
};

export default Home;
