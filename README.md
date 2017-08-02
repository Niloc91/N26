# N26
## Running Application
mvn spring-boot:run

## EndPoints
endpoints are available at

POST
localhost:8080/api/transaction

GET
localhost:8080/api/statistics

## Testing
tests can be run with:
mvn test

## Tech stack
### spring boot dev tools
to allow for faster testing and auto reloading during file edits
### Java 8 streams
to reduce the time complecity of the endpoints

## Time Complexity
Insertion operations in the transaction endpoint takes O(1) as data is put into a HashMap

Retreval operations are O(1) operation as well as data is filtered and returned using streams.


