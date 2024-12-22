import React, { useState, useEffect } from 'react';
import '../css/Home.css';
import flight1 from '../Assets/1.jpg'
import flight2 from '../Assets/2.jpg'
import flight3 from '../Assets/3.jpg'
import flight4 from '../Assets/4.jpg'
import flight5 from '../Assets/5.png'

const Home = () => {
  const [currentImageIndex, setCurrentImageIndex] = useState(0);

  // Array of image sources
  const flightImages = [flight1, flight2, flight3, flight4, flight5];

  // Function to change image every 5 seconds
  useEffect(() => {
    const interval = setInterval(() => {
      setCurrentImageIndex((prevIndex) => (prevIndex + 1) % flightImages.length);
    }, 5000); // Change image every 5 seconds

    return () => clearInterval(interval); // Cleanup interval on component unmount
  }, []);

  return (
    <div className="landing-page">
      {/* Hero Section with Slideshow */}
      <section
        className="hero"
        style={{ backgroundImage: `url(${flightImages[currentImageIndex]})` }}
      >
        <div className="welcome-text">Welcome to FMS</div>

        {/* Left Arrow */}
        <div className="arrow-left">
          &#8592; {/* Left arrow symbol */}
        </div>

        {/* Right Arrow */}
        <div className="arrow-right">
          &#8594; {/* Right arrow symbol */}
        </div>
      </section>

      {/* Other Sections (Features, About, Contact) */}
      <section id="features" className="features">
        <h2>Our Features</h2>
        <div className="feature-cards">
          <div className="feature-card">
            <h3>Flight Scheduling</h3>
            <p>Plan and manage flights with an intuitive scheduling tool.</p>
          </div>
          <div className="feature-card">
            <h3>Passenger Management</h3>
            <p>Track passenger details and preferences seamlessly.</p>
          </div>
          <div className="feature-card">
            <h3>Real-Time Analytics</h3>
            <p>Get real-time insights into flight performance and operations.</p>
          </div>
        </div>
      </section>

    </div>
  );
};

export default Home;