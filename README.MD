# OpenScholar
> A lightweight Spring Boot & Angular application for exploring scholarly publication metadata.

## Project Overview
OpenScholar is a lightweight web application designed to search, explore and visualize scholarly publication metadata.

The application allows users to search scientific publications through the OpenAlex API, browse recently viewed publications, inspect their metadata and export them in multiple formats.

This project was created as a learning exercise to explore concepts commonly found in institutional repositories, including metadata management, interoperability and scientific publication discovery.

It is not intended to reproduce an existing repository solution but rather to demonstrate the use of modern Java and Angular technologies in a realistic domain-driven context.

## Features
- Search publications through the OpenAlex API
- Retrieve detailed publication metadata
- Automatically persist viewed publications
- Browse recently viewed publications
- Prevent duplicate history entries
- Keep only the 10 most recently viewed publications
- Export publication metadata as JSON
- Export publication metadata as Dublin Core XML
- Responsive Angular frontend
- REST API documented with Swagger / OpenAPI

## Screenshots

### Search Page
![image](./docs/images/capture-search-page.png)

### Publication Details Page
![image](./docs/images/capture-details-page.png)

## User Flow
The following diagram illustrates the main user journey through the application.

![image](./docs/images/user-flow.png)

## Architecture
The application follows a simple client-server architecture.

The Angular frontend communicates with a Spring Boot REST API, which retrieves publication metadata from the OpenAlex API and persists viewed publications in PostgreSQL.

![image](./docs/images/architecture.png)

## Technology Stack

### Frontend

- Angular 21
- TypeScript
- Angular Signals
- Angular Router
- HttpClient

### Backend

- Java 21
- Spring Boot 3.5
- Spring Web
- Spring Data JPA
- Flyway
- PostgreSQL 17
- Jackson XML
- SpringDoc OpenAPI

### Testing

- JUnit 5
- Mockito

### Development

- Maven
- Docker Compose

## Backend API

### Search Publications

```http
GET /api/publications/search?query=machine learning
```

Searches publication metadata through the OpenAlex API.

---

### Publication Details

```http
GET /api/publications/{openAlexId}
```

Retrieves publication details from OpenAlex and automatically stores them in the local history.

History behavior:

- unique `openAlexId`
- no duplicate entries
- updates `viewedAt` when already present
- automatically removes the oldest entries when exceeding 10 publications

---

### Viewed Publications

```http
GET /api/publications/viewed
```

Returns the 10 most recently viewed publications stored locally.

---

### JSON Export

```http
GET /api/publications/{openAlexId}/export/json
```

Exports publication metadata as JSON without persisting it in the local history.

---

### Dublin Core XML Export

```http
GET /api/publications/{openAlexId}/export/dublin-core
```

Exports publication metadata as Dublin Core XML without persisting it in the local history.

Response:

```http
Content-Type: application/xml
```

## Getting Started

### Prerequisites

- Java 21
- Node.js
- npm
- Docker
- Docker Compose

---

### Clone the repository

```bash
git clone https://github.com/interalexDev/open-scholar.git
cd open-scholar
```

---

### Option 1: Local Development (Recommended)

#### Start PostgreSQL

```bash
cd database
docker compose up -d
```

#### Run the backend

```bash
cd backend
./mvnw spring-boot:run
```

#### Run the frontend

```bash
cd frontend
npm install
ng serve
```

The application will be available at:

| Service | URL |
|----------|-----|
| Frontend | http://localhost:4200 |
| Backend API | http://localhost:8080 |
| Swagger UI | http://localhost:8080/swagger-ui/index.html |

---

### Option 2: Fully Containerized

Build and start the complete application stack:

```bash
docker compose up --build
```

Or run it in detached mode:

```bash
docker compose up -d --build
```

This starts:

- PostgreSQL
- Spring Boot backend
- Angular frontend

The application will be available at:

| Service | URL |
|----------|-----|
| Frontend | http://localhost:4200 |
| Backend API | http://localhost:8080 |
| Swagger UI | http://localhost:8080/swagger-ui/index.html |

---

### Stop the application

#### Local database

```bash
cd database
docker compose down
```

#### Fully containerized

```bash
docker compose down
```


## Project Structure
```
open-scholar/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── io/github/interalexdev/openscholar/
│   │   │   │       ├── client/          # OpenAlex integration
│   │   │   │       ├── configuration/   # Spring configuration
│   │   │   │       ├── controller/      # REST controllers
│   │   │   │       ├── dto/             # Data transfer objects
│   │   │   │       ├── entity/          # JPA entities
│   │   │   │       ├── mapper/          # Entity / domain mapping
│   │   │   │       ├── model/           # Domain models
│   │   │   │       ├── repository/      # Spring Data repositories
│   │   │   │       └── service/         # Business logic
│   │   │   │
│   │   │   └── resources/
│   │   │       ├── application.yaml     # Backend configuration
│   │   │       └── db/
│   │   │           └── migration/       # Flyway migrations
│   │   │
│   │   └── test/
│   │       └── java/                    # Backend tests
│   │
│   ├── Dockerfile                       # Backend container image
│   └── pom.xml
│
├── frontend/
│   ├── src/
│   │   ├── app/
│   │   │   ├── core/
│   │   │   │   ├── models/              # Shared application models
│   │   │   │   └── services/            # HTTP services
│   │   │   │
│   │   │   └── features/
│   │   │       └── publications/
│   │   │           ├── components/      # Reusable UI components
│   │   │           └── pages/           # Feature pages
│   │   │
│   │   ├── environments/                # Environment configuration
│   │   └── styles.scss                  # Global styles
│   │
│   ├── Dockerfile                       # Frontend container image
│   ├── nginx.conf                       # Nginx config for Angular app
│   ├── package.json
│   └── angular.json
│
├── docs/
│   └── images/                          # Diagrams and screenshots
│
├── database/
│   └── docker-compose.yml               # Standalone PostgreSQL instance for local development
│
├── docker-compose.yml                   # Full local stack
├── README.md
└── LICENSE
```

## References

- Publication data provided by the [OpenAlex](https://openalex.org) API
- Diagrams were created using [Lucidchart](https://www.lucidchart.com).

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.