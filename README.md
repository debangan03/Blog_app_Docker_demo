# Blog Application Documentation

## Overview
The **Blog App** is a simple web application built using **Spring Boot** that provides functionalities to manage blog posts. Users can:

- Retrieve all blog posts.
- Add a new blog post.
- Delete a blog post by its ID.

The application is containerized using **Docker** and utilizes a **PostgreSQL database** running as a Docker container for data persistence.

---

## Technologies Used
- **Spring Boot** (Java-based framework for backend development)
- **PostgreSQL** (Relational Database Management System)
- **Docker** (Containerization platform)
- **Docker Compose** (For managing multi-container applications)
- **SLF4J with Logback** (Logging framework)

---

## Logging Mechanism
The application leverages **Spring Boot's built-in logging mechanism** with **SLF4J and Logback** to log important events such as:

- **Incoming HTTP requests**
- **Database interactions**
- **Application errors and exceptions**

The following logging levels are used:
- `log.error();` → For logging errors
- `log.info();` → For general informational messages

Logs can be monitored in the console or redirected to a log file if configured.

---

## Docker Setup
This project includes a **`docker-compose.yml`** file that defines two main services:

### 1. PostgreSQL Database (`postgres-db`)
- Runs a **PostgreSQL** container.
- Stores blog data persistently using Docker **volumes**.
- Exposes **port 5432** (default PostgreSQL port).
- Configured with environment variables for credentials.

### 2. Spring Boot Application (`blog-app`)
- Runs the **Spring Boot Blog API**.
- Connects to the PostgreSQL database.
- Exposes APIs on **port 8080**.
- Uses **environment variables** for database configuration.

### Running the Application
To start the application using **Docker Compose**, run the following command:
```sh
docker-compose up --build
```
This command builds and runs the application along with the PostgreSQL container.

To stop and remove the containers, use:
```sh
docker-compose down
```

---

## API Endpoints
The Blog App provides the following RESTful APIs:

### 1. Get All Blogs
- **Endpoint:** `GET /api/blog`
- **Description:** Fetches all blog posts from the database.
- **Response:**
  ```json
  [
    {
      "id": 1,
      "title": "First Blog",
      "description": "This is my first blog post."
    }
  ]
  ```

### 2. Add a Blog
- **Endpoint:** `POST /api/blog`
- **Description:** Adds a new blog post.
- **Request Body:**
  ```json
  {
    "title": "New Blog",
    "description": "This is a new blog post."
  }
  ```
- **Response:**
  ```json
  {
    "id": 2,
    "title": "New Blog",
    "description": "This is a new blog post."
  }
  ```

### 3. Delete a Blog
- **Endpoint:** `DELETE /api/blog/{id}`
- **Description:** Deletes a blog post by its ID.
- **Path Variable:** `id` - ID of the blog post to delete.
- **Response:** `204 No Content` (on successful deletion).

---

## Environment Variables
The application uses the following environment variables for configuration:

| Variable             | Description                      |
|----------------------|--------------------------------|
| `POSTGRES_DB`       | PostgreSQL database name       |
| `POSTGRES_USER`     | PostgreSQL username           |
| `POSTGRES_PASSWORD` | PostgreSQL password           |
| `SPRING_DATASOURCE_URL` | JDBC URL for PostgreSQL  |
| `SPRING_DATASOURCE_USERNAME` | Database username |
| `SPRING_DATASOURCE_PASSWORD` | Database password |

These variables can be modified in the **`docker-compose.yml`** file as per requirements.

---

## Additional Notes
- Ensure **Docker** is installed and running before executing any commands.
- The PostgreSQL database persists data across container restarts using **Docker volumes**.
- The application follows a **RESTful API design**.
- API testing can be done using **Postman** or `curl` commands.
- Modify environment variables in `docker-compose.yml` if needed for different configurations.

---

## Future Enhancements
- Implement **user authentication** for blog operations.
- Add **pagination and sorting** for fetching blog posts.
- Implement **Swagger UI** for API documentation.
- Deploy the application to a cloud platform like **AWS** or **Azure**.

---

## Conclusion
This **Blog App** provides a simple and scalable backend service for managing blog posts using **Spring Boot**, **PostgreSQL**, and **Docker**. With containerization, the application can be easily deployed and managed across different environments.
## Author
Debangan Bhattacharyya
