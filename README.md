# ads-challenge
Advertisement Metrics and Recommendation Service

This project is designed to process advertisement events, calculate metrics, and provide recommendations for top-performing advertisers based on the highest revenue per impression rate.
API Documentation

The Swagger API documentation for this service is available at the following URL:

bash
http://{ip}:9090/swagger-ui/index.html#

Technology Stack

    Spring Boot: For building the RESTful API.
    PostgreSQL: Used as the relational database.
    Maven: For project build and dependencies management.
    Swagger: For API documentation and testing.

Project Setup
Prerequisites

    PostgreSQL Database: Ensure PostgreSQL is installed on your machine. You can configure the database credentials in the application-dev.properties file located in the resources folder.

Database Setup

Uncomment the following line in application.properties to enable automatic table creation:

properties
spring.jpa.hibernate.ddl-auto=create

Input Files

Since input test files were not provided, I have created two sample files:

    click.json
    impression.json

These files can be found in the resources folder. The application processes these files to generate the metrics and advertiser recommendations.
Build and Run

    To build the project, run:

    bash
    mvn clean install

    To run the project, use the following command:

    bash
    java -jar ads-challenge-0.0.1.jar

Authentication

Basic authentication is enabled for testing via Swagger. To access the API, use the following credentials:

    Username: test
    Password: test

You can enter these credentials when accessing the API through Swagger.
