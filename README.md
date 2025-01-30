# Blog App

## Overview
This is a simple Blog application built using Spring Boot that allows users to:
- Retrieve all blog posts
- Add a new blog post
- Delete a blog post by ID

The application is containerized using Docker and runs with a PostgreSQL database as a Docker container.

## Logging
This application uses Spring Boot's built-in logging mechanism (SLF4J with Logback) to log incoming requests, database interactions, and application errors.

## Docker Setup
This project includes a `docker-compose.yml` file that defines two services:

1. **PostgreSQL Database (`postgres-db`)**
   - Runs a PostgreSQL container.
   - Stores blog data persistently using Docker volumes.
   - Exposes port `5432`.

2. **Spring Boot Application (`blog-app`)**
   - Runs the Spring Boot Blog API.
   - Connects to the PostgreSQL database.
   - Exposes APIs on port `8080`.
   - Uses environment variables for database configuration.

## Running the Application
To start the application using Docker Compose, run:

```sh
docker-compose up --build
```

To stop the application:

```sh
docker-compose down
```

## Exposed APIs

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
- **Response:** `204 No Content` on success.

## Additional Notes
- Ensure Docker is installed and running on your system before executing the commands.
- The PostgreSQL database persists data across container restarts using a Docker volume.
- Modify environment variables in `docker-compose.yml` if needed for different configurations.

