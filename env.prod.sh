# env.prod.sh
# Production environment for Oracle Autonomous Database via wallet.
#
# wallet service aliases are defined in:
#   ./Wallet_WSAIDBATP/tnsnames.ora

# Use a wallet TNS alias (not a full JDBC descriptor).
export DB_URL="jdbc:oracle:thin:@wsaidbatp_high"

# Your ADB database username and password
export DB_USER="ADMIN"
export DB_PASSWORD='EpicPizza11$69420'

# Spring profile
export SPRING_PROFILES_ACTIVE="prod"

# Backend port (optional, for local run)
export SERVER_PORT=8080

# OCI Always Free tuning
export DB_POOL_SIZE=4
export DB_POOL_MIN_IDLE=0
export DB_CONN_TIMEOUT_MS=10000
export DB_VALIDATION_TIMEOUT_MS=5000
export DB_IDLE_TIMEOUT_MS=180000
export DB_MAX_LIFETIME_MS=600000
export DB_KEEPALIVE_TIME_MS=120000
export TOMCAT_MAX_THREADS=30
export TOMCAT_MIN_SPARE_THREADS=5
export TOMCAT_ACCEPT_COUNT=100
export TOMCAT_CONNECTION_TIMEOUT=10s

# JVM tuning for low-memory compute instances
export JAVA_TOOL_OPTIONS="-XX:+UseG1GC -XX:+UseStringDeduplication -XX:MaxRAMPercentage=65 -XX:InitialRAMPercentage=20"

# Comma-separated allowed frontend origins for CORS.
# For OCI deployment, replace/add your deployed frontend URL(s).
export CORS_ALLOWED_ORIGINS="http://localhost:4200,http://127.0.0.1:4200"

# Wallet mount used by docker-compose.prod.yml
export WALLET_DIR="./Wallet_WSAIDBATP"
export WALLET_CONTAINER_PATH="/app/wallet"

# Optional overrides
# export JPA_DDL_AUTO=validate
# export DB_DRIVER=oracle.jdbc.OracleDriver
# export HIBERNATE_DIALECT=org.hibernate.dialect.OracleDialect
