# Customer Service

## Prerequisites

Ensure you have the following installed on your local machine:

- Docker
- Java JDK

## Setting Up Your Local Environment

1. **Clone the repository:**
   First, clone the `customer-service` repository to your local machine. You can do this by running the following command in your terminal:

    ```bash
    git clone git@github.com:postech-food-challenge/customer-service.git
    ```

   Navigate to the project directory:

    ```bash
    cd customer-service
    ```

2. **Run application:**
   In order to run the application locally, a few steps need to be performed:

    1. **Run docker-compose script:** In the root folder, run:
       ```bash
        docker compose up -d
        ```
    This command will start both customer-service and it's database

That's it! Your local development environment for running customer-service is set up, and you should be able to test our application.

## Postman
You can find the collection for this API by clicking the button bellow

[<img src="https://run.pstmn.io/button.svg" alt="Run In Postman" style="width: 128px; height: 32px;">](https://www.postman.com/palmeiirat/workspace/customer-service/collection/21618886-3f3758db-fd3e-47b1-b9da-66ab23746f7e?action=share&creator=21618886)

## Software Structure
The architecture implemented in our software is the Clean Architecture. Below is a drawing representing this architecture:
![img.png](files/img.png)