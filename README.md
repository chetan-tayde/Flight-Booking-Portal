# Flight Booking System

## Overview
The Flight Booking System is a comprehensive application that allows users to search for flights, book flights. It provides  customers robust features for administrators to manage flights and bookings efficiently.

## Features
- Allows add users with user details
- Search flights by origin, destination, and date.
- Book tickets for available seats in economy and business
- Real-time seat availability updates.
- Search by booking Id to see booked flghts.
- More details are present in each apis belows

## Installation

Follow these steps to set up the project locally:

#### 1. Clone the repository
   
  `git clone https://github.com/chetan-tayde/Flight-Booking-Portal.git`

#### 2. Navigate to the project directory:

  `cd flight-booking-portal`

#### 3. Install dependencies:

   `mvn install`

#### 4. Configure the database:

Update the application.yml file located in src/main/resources/ with your database credentials:

```
spring:
  datasource:
    url: {Your Url}
    username: {Root}
    password: {Password}
    driver-class-name: {Driver class details}
  jpa:
    hibernate:
      ddl-auto: update
    database-platform: {Your dailect details}
```

#### 5. Run the application by right clicking on project run as spring boot application


## Usage
## Oops concept implementation
To achieve oops concept into project i have made some change as below.
### Encapsulation
Encapsulation is achieved by keeping the fields of all classes private and exposing them via public ```getters and setters```.This ensures controlled access to field.
### Polymorphism
Polymorphism is implemented in the ```BookingServiceImpl & FlightServiceImpl``` class using method overloading.
The same method name is used with different parameters to achieve different functionalities based on the input.
### Abstraction
The ```FlightServiceImpl,BookingServiceImpl & UserServiceImpl``` class implements the ```FlightService,BookingService & UserService``` interface resepectively, using OOP principle of abstraction.This ensures service methods improves maintainability.
### Loggers
Implemeneted Loggers for better traceability and debugging.
## Add New User Api

- Post Request http://localhost:8080/api/users (Add new user)
- This API allows you to add a new user to the system.
-  By providing the required user details in the request body, a new user will be registered and stored in the database. Find below JSON Request body.
  
#### Example Request Body
```
  {
    "firstName": "Chetan",
    "lastName": "Tayade",
    "phoneNumber": "7038540342",
    "emailId": "taydechetan983@gmail.com",
    "country": "India"
  }
```
#### Validation Error Handling
  If a required field is missing, the API will return a 400 Bad Request with a helpful error message and handles exception.
```
{
    "errors": {
        "firstName": "Name cannot be empty"
    }
}
```
## Add New Flight Api

 - Post Request:- http://localhost:8080/api/flights  (Add New Flight)
 - This API allows you to save a new flight in the system.
 - By providing all the required flight details in the request body, you can add a new flight to the database.
 - Once created it will create flight with unique flight ID ex. FLT-MUMPUN163 function has been implemented to generate unique flight IDs for every successful flight creation.
 - It also ensures the flight is validated with input sending in Request body. find below request body
    
#### Example Request Body
```
{
    "source": "Kochi",
    "destination": "Delhi",
    "seatingCapacity": "230",
    "depatureDate": "2025-01-15",
    "departureTime": "17:30:40",
    "arrivalDate":"2025-01-15",
    "arrivalTime":"19:30:40",
    "bussinessClassPrice": "1899",
    "economyClassPrice": "1221",
    "airportSourceName":"Kochi International Airport",
    "aiportDestinationName":"Delhi International Airport",
    "aiportSourceCityName": "Kochi",
    "airportDestinationCityName":"Delhi",
    "airLineName":"Delta Air Line"
}
```
#### Validation Error Handling

If a required field are missing, the API will return a 400 Bad Request with a helpful error message and handles the ecception.
```
{
    "errors": {
        "destination": "Destination cannot be empty",
        "source": "Source cannot be empty"
    }
}
```

## Search Flight Api

- Get Request:- http://localhost:8080/api/flights/search?departureDate=2025-01-15&source=kochi&destination=delhi  (Search flight based on requirments)
- Enables users to filter flights based on departure date, source, and destination.
- Only flights matching the criteria will be returned, ensuring precise search results.
- The response includes the current availability of business and economy seats, along with pricing details, Origin, Destination, Airport Details etc
- below is the success message while API retrieves flights that match the specified departure date, source, and destination. here in this 1 flight match so it give one flight if it is more than 1 it will generate list of flights
#### Success Message
```
    {
        "Flight Id": "FLT-KOCDEL505",
        "Source": "Kochi",
        "Destination": "Delhi",
        "Depature Date": "2025-01-15",
        "Departure Time": "17:30:40",
        "Arrival Date": "2025-01-15",
        "Arrival Time": "19:30:40",
        "Business Class Available Seats": 115,
        "Economy Class Available Seats": 115,
        "Bussiness Class Price": 1899.0,
        "Economy Class Price": 1221.0,
        "Source Airport": "Kochi International Airport",
        "Destination Airport": "Delhi International Airport",
        "Air Line": "Delta Air Line"
    }
```
#### Validation Error Handling
if flight is not available for given input or user given wrong input it gives meaningful message and handles the exception.
```
{
    "message": "No flights found for the given criteria."
}
```

## Booking Flight Api
 - Post Request:- http://localhost:8080/api/booking/book?flightId=FLT-KOCDEL50&userId=1&seatClass=BUSINESS  (Books flight based on flightId, userId and Seatclass)
 - This API is used to book a seat on a flight based on the provided input parameters like flightId, userId and seatClass
 - It checks the availability of the requested seat class on the specified flight.
 - If available, books the seat, reduces the available seat count for the respective business and economy class, and generates a booking ID provide response.
#### Success Message 
```
{
    "Status": "Booking is successful",
    "Booking Id": "BK-8687863",
    "Flight Id": "FLT-KOCDEL505",
    "Source": "Kochi",
    "Destination": "Delhi",
    "Depature Date": "2025-01-15",
    "Departure Time": "17:30:40",
    "Arrival Date": "2025-01-15",
    "Arrival Time": "19:30:40",
    "Seat Number": 3,
    "Seat Class": "BUSINESS",
    "Price": 1899.0,
    "Airport Source Name": "Kochi International Airport",
    "Airport Destination Name": "Delhi International Airport"
}
```
#### Validation Error Handling
- If the flight ID, user ID, or seat class is invalid, a meaningful error message is returned.
- If no seats are available in the requested class, the system informs the user appropriately.
```
{
    "message": "User not found for id: 5"
}
{
    "message": "Flight not found for id: FLT-KOCDEL5"
}
```
## Fetch Booking Details API
- Get Request:- http://localhost:8080/api/booking/BK-9981434 (Fetches booking details from bookingId)
- Fetches the booking details from the database using the provided booking ID.
- Returns all relevant information, including details such ad source, destination, dates, times, airport source and destination name,user information, and booking-specific data seat number, class, price.
- function has been implemented to generate unique booking IDs for every successful booking. 
#### Success Message  
```
{
    "Booking Id": "BK-8687863",
    "User Id": 1,
    "First Name": "Chetan",
    "Last Name": "Tayade",
    "Seat Number": 3,
    "Departure Date": "2025-01-15",
    "Departure Time": "17:30:40",
    "Arrival Date": "2025-01-15",
    "Arrival Time": "19:30:40",
    "Source Airport": "Kochi International Airport",
    "Destination Airport": "Delhi International Airport",
    "Price": 1899.0,
    "Seat Class": "BUSINESS"
}
```
#### Validation Error Handling
- if the bookingid is invalid or provided id wrong then system gives meaningful message and handles the exception
```
{
    "message": "Booking not found for id: BK-868786"
}
```

## Fetch All Booking Details API
- Get Request:- http://localhost:8080/api/booking (Fetches all booking details in list)
- Fetches the all booking details from the database.
- Returns all relevant information, including details such ad source, destination, dates, times, airport source and destination name,user information, and booking-specific data seat number, class, price.
#### Success Message
```
    {
        "bookingId": "BK-3395276",
        "user": {
            "userId": 3,
            "firstName": "Vishal",
            "lastName": "Umbarkar",
            "phoneNumber": "7057476885",
            "emailId": "vishalumbarkar@gmail.com",
            "country": "India"
        },
        "flight": {
            "depatureDate": "2025-01-16",
            "departureTime": "18:30:40",
            "arrivalDate": "2025-01-16",
            "arrivalTime": "19:30:40",
            "flightId": "FLT-BANDEL210",
            "source": "Banglore",
            "destination": "Delhi",
            "seatingCapacity": 280,
            "bussinessClassPrice": 2353.0,
            "economyClassPrice": 1931.0,
            "airportSourceName": "Banglore International Airport",
            "aiportDestinationName": "Delhi International Airport",
            "aiportSourceCityName": "Banglore",
            "airportDestinationCityName": "Delhi",
            "airLineName": "Delta Air Line"
        },
        "seatNumber": 141,
        "seatClass": "ECONOMY",
        "price": 1931.0
    },
```

## Search Flight Based on Airport Name.
- Get Request:- http://localhost:8080/api/flights/byAirport?airportSourceName=Pune International Airport&aiportDestinationName=Mumbai International Airport
- Enables users to filter flights based on airportSourceName source and aiportDestinationName.
- Only flights matching the criteria will be returned, ensuring precise search results.
#### Success Message
```
[
    {
        "Flight Id": "FLT-PUNMUM173",
        "Source": "Pune",
        "Destination": "Mumbai",
        "Depature Date": "2025-01-19",
        "Departure Time": "18:30:40",
        "Arrival Date": "2025-01-20",
        "Arrival Time": "19:30:40",
        "Business Class Available Seats": 140,
        "Economy Class Available Seats": 139,
        "Bussiness Class Price": 1785.0,
        "Economy Class Price": 1394.0,
        "Source Airport": "Pune International Airport",
        "Destination Airport": "Mumbai International Airport",
        "Air Line": "Delta Air Line"
    },
    {
        "Flight Id": "FLT-PUNMUM272",
        "Source": "Pune",
        "Destination": "Mumbai",
        "Depature Date": "2025-01-20",
        "Departure Time": "18:30:40",
        "Arrival Date": "2025-01-20",
        "Arrival Time": "19:30:40",
        "Business Class Available Seats": 137,
        "Economy Class Available Seats": 138,
        "Bussiness Class Price": 1785.0,
        "Economy Class Price": 1394.0,
        "Source Airport": "Pune International Airport",
        "Destination Airport": "Mumbai International Airport",
        "Air Line": "Delta Air Line"
    }
]
```


## Summary 
This project has been meticulously designed and developed with a focus on modularity, robustness, and user-centric functionality.
### Controller
- Developed RESTful controllers to handle all incoming API requests effectively.
### Services
- Implemented service classes to encapsulate business logic, ensuring clean code and reusability.Facilitated functionalities like booking management, flight search, and user operations.
### Repositories
- Leveraged Spring Data JPA repositories for data persistence and retrieval.
### Exception Handling
- Created custom exceptions to handle scenarios like invalid input, resource not found, or booking conflicts.
- Implemented a global exception handler to provide meaningful and user-friendly error responses.
### Validation
- Added robust validations on entity fields using annotations like @NotNull, @NotEmpty, and @size to maintain data integrity.
### Utility Methods
- Created helper methods for tasks like generating unique IDs, Constant, enums, seat assignment, and date validation.
