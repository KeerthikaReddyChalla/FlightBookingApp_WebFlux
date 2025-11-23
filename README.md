# **Flight Booking Application**
This project is a Flight Booking System built using Spring Boot + MongoDB
Users can search flights, book tickets, view booking history, and cancel bookings.
Admins can manage airlines and flight inventory.

## 1. Project Overview

This application exposes REST APIs that allow:

### User Features

- Search for flights (by date, origin, destination)

- View airline name, logo, flight timings, and price

- Book flight tickets

- Passenger details

- Seat count

- Receive a system-generated PNR

- View booking history using email ID

- View ticket using PNR

- Cancel booked ticket (only before 24 hours of departure)
  

### Architecture Diagram

<img width="2390" height="861" alt="Architecture-Diagram" src="https://github.com/user-attachments/assets/9b536b8b-9444-4d13-9595-79e8853077f0" />



## 3. Postman Screenshots

### 1) Add Inventory
<img width="2865" height="1406" alt="Screenshot 2025-11-23 154535" src="https://github.com/user-attachments/assets/5581fa99-0173-46f2-ada9-f5559f661efe" />

### 2) Search Inventory
<img width="2866" height="1447" alt="Screenshot 2025-11-23 154943" src="https://github.com/user-attachments/assets/3b01f060-8254-4bc8-b9a5-2b4299f20d49" />

### 3) Book Ticket
<img width="2870" height="1611" alt="Screenshot 2025-11-23 155120" src="https://github.com/user-attachments/assets/34b8e2fa-e2c6-47f2-93e9-5e431fffb134" />

### 4)Get Booking history by email
<img width="2872" height="1545" alt="Screenshot 2025-11-23 155519" src="https://github.com/user-attachments/assets/877c3df7-1d23-4465-a88d-6e85918beb6b" />

### 5)Delete ticket
<img width="2873" height="1462" alt="Screenshot 2025-11-23 160126" src="https://github.com/user-attachments/assets/314ae314-39c1-4858-8c03-dc6f6d534428" />



## 4. Database Schema & Screenshots (MongoDB)

### 1) Inventories collection
<img width="2852" height="1596" alt="image" src="https://github.com/user-attachments/assets/3cbdc405-5ea0-445a-90cb-52d093dae9bc" />

### 2) Bookings collection
<img width="2814" height="1631" alt="image" src="https://github.com/user-attachments/assets/85bdaf4e-6840-49b9-8c27-6193b05ae447" />

### 3) airlines collection
<img width="2807" height="1610" alt="image" src="https://github.com/user-attachments/assets/2fca94cd-ee4b-436b-b339-67d66b5160e2" />

