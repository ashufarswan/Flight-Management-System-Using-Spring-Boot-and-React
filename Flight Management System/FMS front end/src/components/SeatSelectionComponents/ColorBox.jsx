import React from 'react';
import './FlightSeats.css'; // Make sure to include your CSS here
 
const ColorBox = ({ color, description }) => {
  return (
    <div className="color-box">
      <div className="color-swatch" style={{ backgroundColor: color }}></div>
      <div className="color-description">{description}</div>
    </div>
  );
};
 
export default ColorBox;