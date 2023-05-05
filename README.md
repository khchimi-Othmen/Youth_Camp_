# Online_Store

This repository contains a web development project for creating an online store. The project uses a layered architecture with multiple layers including MVC, AOP, DTO, Validator, and custom exceptions. The project also uses Stripe, PDF, and email APIs. JUnit was used to test the project's methods. In addition, this project uses a MySQL server for data storage and retrieval. Swagger configuration and cross-origin configuration have also been implemented in this project. A scheduler has been added to send email notifications to the admin about top and low in stock statistics.

## Project Architecture

The online store project uses a layered architecture with multiple layers including:

- **Model:** This layer contains the data access logic and interacts with the database to perform CRUD operations on the data.
- **View:** This layer contains the user interface and is responsible for displaying the data to the user.
- **Controller:** This layer handles the user input and interacts with the model and view layers to process the data.
- **Aspect:** This layer contains the cross-cutting concerns such as logging and security.
- **DTO:** This layer contains the data transfer objects that are used to transfer data between layers.

## APIs

This project uses the following APIs:

- **Stripe API:** This API is used to handle payment processing for the online store.
- **PDF API:** This API is used to generate PDF documents for the online store.
- **Email API:** This API is used to send emails to customers.

## Testing

JUnit was used to test the project's methods. This ensures that the project's functions are working as expected and that any issues are caught and resolved early in the development process.

## MySQL Server

This project uses a MySQL server for data storage and retrieval. The MySQL server is used to store customer data, product data, and order data.

## Swagger Configuration

This project includes Swagger configuration, which provides an interactive API documentation for the project. Swagger documentation can be accessed by navigating to `http://localhost:8080/swagger-ui.html` in your browser.

## Cross-Origin Configuration

This project includes cross-origin configuration, which allows the application to access resources from other domains. Cross-origin configuration is necessary when the application is deployed on a different server than the API.

## Scheduler

A scheduler has been added to send email notifications to the admin about top and low in stock statistics.

## Getting Started

To get started with this project, follow the steps below:

1. Clone the repository to your local machine.
2. Install the dependencies using `mvn install` command.
3. Start the application using `mvn spring-boot:run` command.
4. Navigate to `http://localhost:8080/` in your browser to view the application.

## Contributions

Contributions to this project are welcome! Please feel free to submit pull requests or raise issues for any bugs or feature requests.

## License

This project is licensed under the [MIT license](https://opensource.org/licenses/MIT).
