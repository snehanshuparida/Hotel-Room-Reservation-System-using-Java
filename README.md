# Hotel Room Reservation System

A simple console-based **Hotel Room Reservation System** built in Java. It allows users to book rooms, check availability, cancel bookings, and generate invoices, with data stored in a MySQL database.

## Features

- **Book Room**
  - Shows all currently available rooms
  - Calculates total amount based on room price per night and number of days
  - Creates a booking record and marks the room as unavailable

- **Check Room Availability**
  - Lists rooms that are currently available for booking

- **Cancel Booking**
  - Cancels an existing booking by booking ID
  - Frees the room by marking it as available again

- **Generate Invoice**
  - Prints a simple invoice with booking details, room type, price per night, days stayed, and total amount

## Tech Stack

- **Language:** Java (console application)
- **Database:** MySQL
- **JDBC:** Direct JDBC access using `java.sql` package

## Project Structure

- `HotelReservationSystem.java`  
  Main class with menu, input handling, and high-level flow (book room, check availability, cancel booking, generate invoice).

- `Room.java`  
  Model class representing a hotel room (`roomId`, `roomType`, `pricePerNight`, `available`).

- `RoomDAO.java`  
  Data Access Object for rooms. Fetches available rooms, gets room by ID, and updates room availability.

- `Booking.java`  
  Model class representing a booking (`bookingId`, `customerName`, `roomId`, `numberOfDays`, `totalAmount`).

- `BookingDAO.java`  
  Data Access Object for bookings. Creates, reads, and deletes bookings using JDBC.

- `DBUtil.java`  
  Utility class for creating a JDBC connection to the MySQL database.

## Database Setup

1. Create a MySQL database (example name: `hotel_db`).
2. Create tables similar to:

```sql
CREATE TABLE rooms (
    room_id INT AUTO_INCREMENT PRIMARY KEY,
    room_type VARCHAR(50) NOT NULL,
    price_per_night DOUBLE NOT NULL,
    is_available BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE bookings (
    booking_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(100) NOT NULL,
    room_id INT NOT NULL,
    number_of_days INT NOT NULL,
    total_amount DOUBLE NOT NULL,
    FOREIGN KEY (room_id) REFERENCES rooms(room_id)
);
```

3. Insert some sample rooms:

```sql
INSERT INTO rooms (room_type, price_per_night, is_available) VALUES
('Single', 1500, TRUE),
('Double', 2500, TRUE),
('Suite', 5000, TRUE);
```

4. Update `DBUtil.java` with your own MySQL URL, username, and password:

```java
private static final String URL = "jdbc:mysql://localhost:3306/hotel_db";
private static final String USER = "your_username";
private static final String PASSWORD = "your_password";
```

## How to Run

1. Make sure MySQL is running and the database/tables are created.
2. Ensure the MySQL JDBC driver (for example `mysql-connector-j`) is on the project classpath.
3. Compile the Java files (from the project root where the `hotel` package is located):

```bash
javac hotel/*.java
```

4. Run the main application:

```bash
java hotel.HotelReservationSystem
```

5. Use the console menu to:
   - Book a room
   - Check availability
   - Cancel a booking
   - Generate an invoice

You can customize room types, prices, and database credentials as needed.
