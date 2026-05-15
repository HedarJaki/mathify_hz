# Mathify

A focused yet casual math learning platform with gamification elements. Mathify combines the engaging progression mechanics of apps like Duolingo with a more formal course structure — covering topics from basic arithmetic to linear algebra and calculus.

## Features

- Structured math courses (Basic Math, Calculus, Linear Algebra, and more)
- Gamified learning experience (XP, streaks, progression)
- Course-based curriculum with formal lesson structure
- Web-based, accessible from any browser

## Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java Servlets (Jakarta EE) |
| Frontend | JSP, HTML/CSS/JS |
| Database | PostgreSQL |
| Build Tool | Maven |
| Runtime | Apache Tomcat 10 |
| Containerization | Docker |

## Prerequisites

- [Docker](https://www.docker.com/) and Docker Compose
- Java 17+
- Maven 3.9+ (or use the included `mvnw` wrapper)

## Getting Started

### Run with Docker (recommended)

```bash
docker compose up --build -d
```

The app will be available at `http://localhost:8080`.

To stop:

```bash
docker compose down
```

### Run locally (without Docker)

```bash
./mvnw tomcat7:run
```

Or on Windows:

```bash
mvnw.cmd tomcat7:run
```

### Build only

```bash
./mvnw package -DskipTests
```

The compiled WAR will be at `target/mathify.war`.

## Project Structure

```
src/
└── main/
    ├── java/
    │   └── com/mathify/         # Java source files (Servlets, models, services)
    ├── resources/               # Config files, SQL scripts
    └── webapp/
        ├── WEB-INF/
        │   └── web.xml          # Servlet configuration
        └── *.jsp                # JSP view templates
```

## Environment Variables

| Variable | Description | Default |
|---|---|---|
| `DB_HOST` | PostgreSQL host | `db` |
| `DB_PORT` | PostgreSQL port | `5432` |
| `DB_NAME` | Database name | `mathify` |
| `DB_USER` | Database user | `mathify_user` |
| `DB_PASSWORD` | Database password | *(set in compose.yaml)* |

## Team

Mathify is developed by **Hapis Supremacy** — a team of Informatics students at Telkom University.

## License

This project is for academic purposes.
