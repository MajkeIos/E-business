# Tests

Files necessary to complete sixth exercise for e-business lab.

### Requirements
- Maven 3.x
- Node >= 18.x
- Java 21

### Run
To execute test scenarios we first need to run both frontend and backend.
- **Frontend**:
```bash
cd frontend && npm install && npm start
```
After it starts you can access the application on `localhost:3000`

- **Backend**:
```bash
cd backend && ./mvnw clean package && ./mvnw spring-boot:run
```
After it starts you can access the application on `localhost:8080`

- **Tests**
```bash
cd frontend && npx cypress run
```
