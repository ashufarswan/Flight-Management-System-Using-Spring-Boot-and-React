
## Flight Management System
The Flight Management System is a web application designed to manage airline operations, including flight scheduling, booking, and passenger management. This system is built using Spring Boot for the backend and React for the frontend.
## Features

- **User Authentication** : Secure login and registration for users and administrators.
- **Booking Management**: Book, view, and cancel flight reservations.
- **Admin Dashboard**: Create, update, and delete flight schedules.
- **Payment Integration**: Razorpay test payment Integration.

## Tech Stack

**Backend:**  Spring Boot,Spring Data JPA,Spring Security,RESTful API

**Frontend:** React,React Router,Context,Axios

**Database:** MySql

**Build Tools:** Maven
## Deployment

- **Clone the repository**:
```bash
  git clone https://github.com/ashufarswan/Flight-Management-System-Using-Spring-Boot-and-React.git

```

- **Database Configuration**:
Change these properties in application.properties of each microservices.

```bash
spring.datasource.url= your_url
spring.datasource.username= your_username
spring.datasource.password= your_password

```

- **Front End**:
Open cmd in FMS frontend folder.

-> install node modules:
```bash
npm i

```
-> start front end 

```bash
npm start

```
## How to add Admin ?
    -> Register a user
    -> In user database change role to ADMIN. No that user is registered as an admin. 

## Process Flow
- **User Registration and Login:**  User register and log in using secure authentication mechanisms. Administrators also log in to access additional management features. User details are stored in user database.
- **Flight Management:** Admins create, update, and delete flight schedules.Flight details are stored in the flights database.
- **Booking Management:** Users can book flights by selecting available schedules. Booking details are stored and can be viewed,confirmed or canceled by users.
- **Passenger Management:** Users add passenger details during booking.

** Admin can only delete flight which is not booked yet. 


## Process Flow for Booking a Ticket

 **User Login** ->  **Book Flight** ->  **Confirm Booking** ->  **Seat Selection** -> **Payment** -> **Download Ticket**


