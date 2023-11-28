# Congestion Tax Calculation Application

This Spring Boot application calculates congestion tax based on specified rules for different cities.

## Prerequisites

Make sure you have the following installed:

- Java 11
- Maven

## Build

To build the application, run the following Maven command in the project root directory:

```bash
mvn clean install -DskipTests=True
```

## Run

After building the application, you can run it using the following command:

```bash
java -jar target/congestion-tax-application.jar
```

The application will start on `http://localhost:8090` by default. You can change the port in the `application.yml` configuration file.

## Test

To run tests, use the following Maven command:

```bash
mvn test
```

This will execute the unit tests for the application.

## API Endpoint

The application provides a RESTful API endpoint for calculating congestion tax. You can use tools like `curl` or Postman to send requests.

- **Endpoint**: `POST /calculate-tax/{city}`
- **Request Body**: JSON payload with vehicle type and dates
  ```json
    {
        "vehicle": "car",
        "dates": [
            "2013-04-03 21:00:00",
            "2013-01-01 21:00:00",
            "2013-01-01 06:23:27",
            "2013-10-22 15:27:00"
        ]
    }
  ```
- **Example Request**:
  ```bash
    curl --location 'http://127.0.0.1:8090/calculate-tax/gothenburg' \
    --header 'Content-Type: application/json' \
    --data '{
        "vehicle": "car",
        "dates": [
            "2013-04-03 21:00:00",
            "2013-01-01 21:00:00",
            "2013-01-01 06:23:27",
            "2013-10-22 15:27:00"
        ]
    }'
  ```

## Configuration

City-specific tax configurations are defined in the `application.yml` file under the `tax-configurations` section.

```yaml
tax-configurations:
  configurations:
    - city: Gothenburg
      rules:
        # ... (configuration details)
    - city: AnotherCity
      rules:
        # ... (configuration details)
```

Adjust the configurations as needed for different cities.


## Future Improvements (If more time was given)

While the current application provides a basic implementation for congestion tax calculation, there are several areas that could be enhanced or additional features that could be implemented with more time and resources:

### 1. Security

Implementing proper security measures, such as authentication and authorization, would be essential for a production-ready application. This ensures that only authorized users can access the tax calculation endpoint.

### 2. Logging

Enhance logging throughout the application to facilitate debugging.

### 3. API Documentation

Generate comprehensive API documentation using tools like Swagger or Springdoc. This documentation would help developers understand how to interact with the API and provide details on request and response formats.

### 4. Configuration Management

Consider externalizing configuration settings, allowing for easier configuration changes without modifying the code. Tools like Spring Cloud Config can be integrated for dynamic configuration.

### 5. Error Handling

Improve error handling and provide meaningful error messages to users. This includes handling invalid input gracefully and returning appropriate HTTP status codes.

### 6. Unit and Integration Tests

Expand test coverage to include more edge cases and integration tests. Automated testing ensures the application behaves as expected during changes and updates.
Currently few tests fail.

### 7. Dockerization

Containerize the application using Docker for easier deployment and scalability. This allows for consistent deployment across different environments.

### 8. Continuous Integration/Continuous Deployment (CI/CD)

Set up a CI/CD pipeline to automate the testing and deployment process. This ensures that changes are thoroughly tested and can be deployed seamlessly.

### 9. Environment Profiles

Create different profiles for various environments (development, testing, production). This enables the application to adapt its configuration based on the specific requirements of each environment, improving maintainability and minimizing configuration errors.
