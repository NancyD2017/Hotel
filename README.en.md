# Hotel Booking System

A full-featured **REST API backend** for hotel management — covering hotels, rooms, bookings, ratings, and analytics — built with Spring Boot and MongoDB.

---

## Screenshots

<!-- Screenshot: Swagger UI overview showing all available API endpoints grouped by controller (User, Hotel, Room, Booking, Statistics) -->
![Swagger UI](docs/swagger-ui.png)

<!-- Screenshot: Example API response for hotel search with filters (city, rating, distance from center) — show JSON response body in Postman or similar tool -->
![Hotel Search Response](docs/hotel-search.png)

<!-- Screenshot: CSV statistics export file opened in Excel or similar, showing booking and registration event rows -->
![Statistics Export](docs/statistics-export.png)

---

## Features

### User Management
- Register, find, update, and delete users
- Kafka integration: publishes new user events to `statistics-users` topic

### Hotel Management
- Full CRUD for hotel objects
- Filtering by name, city, address, rating, distance from center
- Paginated responses
- Rating system with validation

### Room Management
- Add and edit rooms
- Advanced search with multiple filters
- Dynamic pagination

### Booking System
- Create and manage bookings
- Admin view of all bookings
- Kafka integration: publishes booking events to `statistics-bookings` topic

### Statistics & Analytics
- Event logging for user registrations and bookings
- Export all statistics to CSV file

---

## Tech Stack

| Category | Technology |
|---|---|
| Language | Java 17+ |
| Framework | Spring Boot, Spring Security |
| Database | MongoDB |
| Messaging | Apache Kafka |
| Infrastructure | Docker, Docker Compose |
| Build tool | Gradle |

---

## Getting Started

### Prerequisites
- Docker 20.10+ and Docker Compose 2.20+
- Java 17+

### Local Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/NancyD2017/Hotel.git
   cd Hotel
   ```

2. **Start MongoDB via Docker**
   ```bash
   cd src/main/resources
   docker-compose up -d
   ```

3. **Run the application**
   ```bash
   ./gradlew bootRun
   ```

4. API available at: `http://localhost:8080/api/`

---

## API Overview

| Resource | Endpoint | Methods |
|---|---|---|
| Users | `/api/users` | GET, POST, PUT, DELETE |
| Hotels | `/api/hotels` | GET, POST, PUT, DELETE |
| Rooms | `/api/rooms` | GET, POST, PUT, DELETE |
| Bookings | `/api/bookings` | GET, POST |
| Statistics | `/api/statistics` | GET, GET (CSV export) |

---

## Sample Statistics Export

The `/api/statistics/export` endpoint generates a CSV file (see `Statistics.csv` in the repository root for a sample output).

---

## License

Educational project. Built as part of a backend development course at Skillbox.
