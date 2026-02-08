# 🚖 RideShare – Ride Booking & Management Backend

RideShare is a **backend-focused ride booking and management system** built using **Java and Spring Boot**.  
The system handles **user management, ride requests, driver assignment, trip lifecycle, and fare calculation**, with a strong emphasis on **clean REST APIs, asynchronous processing, and scalable backend design**.

This project was built to simulate **real-world backend challenges** commonly found in ride-hailing platforms.

---

## 🧩 System Overview

The RideShare backend is designed to:
- Manage users and drivers
- Handle ride booking and trip status updates
- Process ride-related events asynchronously
- Persist ride and trip data reliably
- Expose clean REST APIs for frontend or mobile clients

A **basic frontend UI** was used only for **API testing and flow validation**.  
The primary focus of this project is **backend development**.

---

## 🛠️ Tech Stack

### Backend
- Java
- Spring Boot
- REST APIs

### Messaging
- Apache Kafka

### Database
- MySQL

### Tools
- Docker
- Git

---

## ⚙️ Core Features

### 👤 User & Driver Management
- User registration and profile handling
- Driver availability and assignment logic

### 🚕 Ride Booking Flow
- Create ride requests
- Assign drivers to rides
- Track ride status (requested, accepted, in-progress, completed)
- Calculate fares based on trip details

### 📢 Asynchronous Event Processing
- Used **Apache Kafka** to publish and consume ride-related events
- Enabled non-blocking processing for notifications and ride status updates
- Improved system responsiveness and decoupled services

### 🗄️ Persistent Storage
- Stored users, rides, and trip history in **MySQL**
- Designed schemas to support trip lifecycle tracking

### 🐳 Containerization
- Containerized backend services using **Docker**
- Ensured consistent local development and testing environments

---

## 🔄 High-Level Flow

1. User sends a ride request via REST API  
2. Backend processes the request and assigns a driver  
3. Ride events are published to Kafka topics  
4. Consumers process events asynchronously (status updates, notifications)  
5. Ride and trip data is persisted in MySQL  

---

## 📌 Design Focus

- Backend-first architecture
- Clean separation of concerns (Controller → Service → Repository)
- Asynchronous communication using Kafka
- Scalable and maintainable REST API design
- Production-like local setup using Docker

---

## 🚀 How to Run Locally

> Prerequisites:
- Java 17+
- Docker
- MySQL
- Kafka (or Docker-based Kafka setup)

Basic steps:
1. Clone the repository
2. Configure database and Kafka properties
3. Build the project using Maven
4. Run services locally or using Docker

---

## 🎯 Learning Outcomes

- Designed backend systems similar to real-world ride-hailing platforms
- Gained hands-on experience with **Spring Boot REST APIs**
- Implemented **event-driven architecture** using Kafka
- Worked with relational databases and schema design
- Used Docker for backend service containerization

---

## 📬 Contact

**Manoj Kushwah**  
📧 manojkushwah91115@gmail.com  
🔗 GitHub: https://github.com/manojkushwah91  
🔗 LinkedIn: https://linkedin.com/in/manojkushwah871
