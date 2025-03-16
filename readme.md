# User Grid Service Application

## Overview

The **User Grid Service** is a Spring Boot-based web service that manages and provides access to user data. It exposes various API endpoints that allow clients to retrieve user information based on different criteria such as role, age, ID, or SSN. It also includes appropriate exception handling to ensure that errors are managed gracefully.

This application is built using Spring Boot and includes the following components:

- **Controller Layer**: Handles HTTP requests and responses.
- **Service Layer**: Contains the business logic for user-related operations.
- **Repository Layer**: Interacts with the database to fetch or persist user data.
- **Exception Handling**: Proper handling for cases like user not found or invalid data inputs.
- **Logging**: Provides detailed logging for debugging and monitoring.

## Features

1. **Get All Users**: Retrieves all users in the system.
2. **Get Users by Role**: Retrieves users based on a specific role.
3. **Get Users Sorted by Age**: Retrieves users sorted by age in ascending or descending order.
4. **Get User by ID**: Retrieves a user by their ID.
5. **Get User by SSN**: Retrieves a user based on their SSN (Social Security Number).
6. **Exception Handling**: Handles cases such as user not found or invalid inputs.
7. **Logging**: Provides detailed logging for debugging and monitoring.

## Prerequisites

To run the application, the following are required:

- **Java 17**: The application is built using Java 11.
- **Maven**: For managing dependencies and building the project.
- **Spring Boot**: The framework used for building the application.
- **Database**: H2 in memory database.

## Tech Stack

- **Spring Boot**: For building the RESTful application.
- **Spring Data JPA**: For interacting with the database.
- **Slf4j**: For logging application events.
- **JUnit 5**: For unit testing the application.
- **H2 Database (for testing)**: In-memory database for testing purposes.
- **Maven**: For dependency management and building the application.


## API Endpoints

This section describes all available API endpoints in the **User Grid Service**.

### 1. **Get All Users**

**Endpoint**: `GET /api/users`

**Description**: Retrieve all users from the system.

### 2. **Get Users by Role**

**Endpoint**: `GET /api/users/role/{role}`

**Description**: Retrieve users by their role.

- **Path Variable**:
  - `role`: The role of the user (e.g., `Admin`, `User`).

### 3. **Get Users Sorted by Age**

**Endpoint**: `GET /api/users/sorted`

**Description**: Retrieve users sorted by their age in ascending or descending order.

- **Request Parameter**:
  - `sortOrder`: Specifies the order in which users should be sorted. Acceptable values are:
    - `asc`: Sort by age in ascending order.
    - `desc`: Sort by age in descending order.

### 4. **Get User by ID**

**Endpoint**: `GET /api/users/{id}`

**Description**: Retrieve a single user by their unique ID.

- **Path Variable**:
  - `id`: The ID of the user you want to retrieve.

### 5. **Get User by SSN**

**Endpoint**: `GET /api/users/ssn/{ssn}`

**Description**: Retrieve a single user by their SSN (Social Security Number).

- **Path Variable**:
  - `ssn`: The SSN of the user you want to retrieve.
