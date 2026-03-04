# FlightFoodService Backend

Spring Boot backend for the FlightFoodService application.

## Prerequisites

- Docker Desktop (or Docker Engine + Docker Compose plugin)
- Java 21+ and Maven 3.8+ (only needed when running outside Docker)

## Environment Files

Use the committed env files directly and edit values as needed.

```bash
vi .env.local
vi .env.prod
```

### Local testing variables (`.env.local`)

```dotenv
SPRING_PROFILES_ACTIVE=dev
SERVER_PORT=8080
POSTGRES_DB=flightsdb
POSTGRES_USER=flight_user
POSTGRES_PASSWORD=flight_password
POSTGRES_PORT=5432
```

### Production / OCI variables (`.env.prod`)

```dotenv
DB_URL=jdbc:oracle:thin:@wsaidbatp_high
DB_USER=ADMIN
DB_PASSWORD=REPLACE_WITH_ADB_ADMIN_PASSWORD
DB_DRIVER=oracle.jdbc.OracleDriver
HIBERNATE_DIALECT=org.hibernate.dialect.OracleDialect
SPRING_PROFILES_ACTIVE=prod
SERVER_PORT=8080
CORS_ALLOWED_ORIGINS=http://<OCI_PUBLIC_IP_OR_DOMAIN>:4200
WALLET_DIR=./Wallet_WSAIDBATP
WALLET_CONTAINER_PATH=/app/wallet
```

Set `CORS_ALLOWED_ORIGINS` to the exact frontend URL(s) that will call the API, comma-separated.
Example: `CORS_ALLOWED_ORIGINS=http://89.168.124.17:4200`

## Run with Docker Compose

### Simple commands

```bash
npm run docker:up -- --env=dev
npm run docker:down -- --env=dev
npm run docker:up -- --env=prod
npm run docker:down -- --env=prod
```

Optional flags:

- `--foreground` to run attached
- `--no-build` to skip image rebuild
- `--volumes` (only with `docker:down`) to remove DB volume

### Local testing (backend + PostgreSQL)

```bash
docker compose --env-file .env.local up --build
```

Backend URL: `http://localhost:8080`

## Seed Data (2026)

- Seed script: `db/seed_2026.sql`
- The script is auto-applied only when Postgres initializes a brand-new volume.

Reseed an already-running local DB:

```bash
docker compose --env-file .env.local exec -T db \
  psql -U flight_user -d flightsdb -v ON_ERROR_STOP=1 -f - < db/seed_2026.sql
```

Reset containers and force fresh init (will wipe local DB volume):

```bash
docker compose --env-file .env.local down -v
docker compose --env-file .env.local up --build
```

### Production-style run for OCI (backend only, external DB)

```bash
source env.prod.sh
docker compose -f docker-compose.prod.yml up --build -d
```

Production compose reads variables from `.env.prod` via `env.prod.sh`.

## Run without Docker

```bash
source env.local.sh
./mvnw spring-boot:run
```

## Build Jar

```bash
./mvnw clean package
java -jar target/FlightFoodService-0.0.1-SNAPSHOT.jar
```

## API Endpoints

### Flights

| Method | Endpoint        | Description               | Request Body  |
| ------ | --------------- | ------------------------- | ------------- |
| GET    | `/flights`      | Get all flights           | None          |
| GET    | `/flights/{id}` | Get a flight by ID        | None          |
| POST   | `/flights`      | Create a new flight       | `Flight` JSON |
| PUT    | `/flights/{id}` | Update an existing flight | `Flight` JSON |
| DELETE | `/flights/{id}` | Delete a flight by ID     | None          |

### Orders

| Method | Endpoint       | Description           | Request Body       |
| ------ | -------------- | --------------------- | ------------------ |
| GET    | `/orders`      | Get all orders        | None               |
| GET    | `/orders/{id}` | Get an order by ID    | None               |
| POST   | `/orders`      | Create a new order    | `FlightOrder` JSON |
| DELETE | `/orders/{id}` | Delete an order by ID | None               |

### Food Options

| Method | Endpoint             | Description                | Request Body      |
| ------ | -------------------- | -------------------------- | ----------------- |
| GET    | `/food-options`      | Get all food options       | None              |
| GET    | `/food-options/{id}` | Get a food option by ID    | None              |
| POST   | `/food-options`      | Create a new food option   | `FoodOption` JSON |
| DELETE | `/food-options/{id}` | Delete a food option by ID | None              |

### Ordered Food Items

| Method | Endpoint              | Description                       | Request Body           |
| ------ | --------------------- | --------------------------------- | ---------------------- |
| GET    | `/ordered-items`      | Get all ordered food items        | None                   |
| POST   | `/ordered-items`      | Create a new ordered food item    | `OrderedFoodItem` JSON |
| DELETE | `/ordered-items/{id}` | Delete an ordered food item by ID | None                   |
