# Frontend

Files necessary to complete fifth exercise for e-business lab.

### Requirements
- Maven 3.x
- Node >= 18.x
- Java 21
- Docker

### Docker run
To run the application on Docker execute following command:
```bash 
./build.sh
```
To access the application open following links in your browser:
- **Frontend**: `http://localhost:3000`
- **Backend**: `http://localhost:8080`

### Local run
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
