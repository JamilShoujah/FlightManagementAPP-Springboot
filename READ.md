# FlightFoodService Backend

Spring Boot backend for the FlightFoodService application.

---

## **Project Structure**

```

FlightFoodService/
├── env.example.sh       # Template for environment variables (committed)
├── env.local.sh         # Real secrets (ignored)
├── src/
│   ├── main/
│   │   ├── java/        # Java source code
│   │   └── resources/
│   │       ├── application.yml
│   │       ├── application-dev.yml
│   │       └── application-prod.yml
│   └── test/            # Unit & integration tests
├── pom.xml
└── README.md

```

---

## **Prerequisites**

- Java 21+
- Maven 3.8+
- PostgreSQL database
- Git

---

## **Setup Environment Variables**

1. Copy the template `env` file:

```bash
cp env.example.sh env.local.sh
```

2. Edit `env.local.sh` with your **local database credentials**:

```bash
export DB_URL=jdbc:postgresql://localhost:5432/flightsdb
export DB_USER=jamilshoujah
export DB_PASSWORD=Jamil12345
export SPRING_PROFILES_ACTIVE=dev
export SERVER_PORT=8080
```

3. Load the environment variables into your shell:

```bash
source env.local.sh
```

> 💡 This ensures sensitive data is **never hardcoded** or committed.

---

## **Database Setup**

Make sure PostgreSQL is running and a database exists:

```sql
CREATE DATABASE flightsdb;
```

> Spring Boot will automatically create/update tables on startup (`hibernate.ddl-auto=update`).

---

## **Running the Backend**

### **Development**

```bash
# Load environment variables
source env.local.sh

# Run Spring Boot in dev profile
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

- Default port: `8080` (change in `env.local.sh` if needed)
- SQL logs are printed in the console for debugging.

---

### **Production**

```bash
# Load production environment variables
source env.prod.sh

# Run Spring Boot in prod profile
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

> Make sure `env.prod.sh` contains production DB credentials.

---

## **Build Jar**

```bash
# Clean and build
mvn clean package

# Run the jar
java -jar target/FlightFoodService-0.0.1-SNAPSHOT.jar
```

---

## **Notes**

- Do **not commit `.env.local.sh`** — secrets should stay local.
- Commit `env.example.sh` so new devs can quickly set up.
- Profiles (`dev`, `prod`) allow **different DBs, ports, or logging**.
