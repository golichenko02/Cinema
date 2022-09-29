# CINEMA API

Simple REST API for cinema.

## How to run an application
- Clone repository to your local environment. 
- Run the following command in order to start Postgres DB: `docker run --name cinema  -e POSTGRES_PASSWORD=root -p 5435:5432  -d postgres`.
- Run the following command in order to run an application: `mvn spring-boot:run`.


## Requirements:

- [x] Implement the REST API for a cinema with 2 controllers (orders, movies)
  
- [x] Each controller implements basic CRUD operations.

- [x] Immplement read operations in 2 variations: 
  - Retrieve by id
  - Find All by any parameter of an entity using javax.persistence.criteria and
    springframework.data.jpa.domain.Specification along with pagination.

- [x] DB creation should be implemented with Liquibase.
- [ ] Unit tests
- [ ] Integration tests 
- [x] Host your code on GitHub and provide instructions for running it in README file.
