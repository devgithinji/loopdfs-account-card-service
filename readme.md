# Accounts and Cards Microservice

The Accounts and Cards microservice is fully implemented in Java using the Spring Boot framework. 

The development process leveraged the following 3rd-party libraries:

- Lombok: Used to reduce boilerplate code.

## Running the Project Locally

1. Clone the project from GitHub:

    ```bash
    git clone <repo_url>
    ```

2. Setup project dependencies:

    ```bash
    mvn clean install
    ```

3. If you have Docker Compose installed, run the project:

    - Build the Docker image:

        ```bash
        docker build -t densoftdev/account-card-service:latest .
        ```

    - Push the image to Docker Hub:

        ```bash
        docker push densoftdev/account-card-service
        ```

    - Run the project with Docker Compose:

        ```bash
        docker-compose up -d
        ```
      API documentation opens here locally
   
      [ http://localhost:8080/swagger-ui/index.html#/]( http://localhost:8080/swagger-ui/index.html#/)
  
## Running the Project in a Production Setup via Kubernetes

1. Set up your Kubernetes cluster in a production environment (e.g., AWS EKS or GKE).

2. Move to the `/kubernetes` directory and apply the following command to set up your workload:

    ```bash
    kubectl apply -f .
    ```

3. The live project hosted on a Kubernetes cluster is accessible via:

   [http://35.232.21.255/swagger-ui/index.html#/](http://35.232.21.255/swagger-ui/index.html#/)

Feel free to explore the Swagger UI for API documentation.
