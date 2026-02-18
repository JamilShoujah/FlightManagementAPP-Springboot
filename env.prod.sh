# env.prod.sh
# Production environment for Oracle Autonomous Database via wallet.
#
# wallet service aliases are defined in:
#   ./Wallet_TUC50B42BDEI3XAY/tnsnames.ora

# Use a wallet TNS alias (not a full JDBC descriptor).
export DB_URL="jdbc:oracle:thin:@tuc50b42bdei3xay_high"

# Your ADB database username and password
export DB_USER="ADMIN"
export DB_PASSWORD='EpicPizza11$69420'

# Spring profile
export SPRING_PROFILES_ACTIVE="prod"

# Backend port (optional, for local run)
export SERVER_PORT=8080

# Comma-separated allowed frontend origins for CORS.
# For OCI deployment, replace/add your deployed frontend URL(s).
export CORS_ALLOWED_ORIGINS="http://localhost:4200,http://127.0.0.1:4200"

# Wallet mount used by docker-compose.prod.yml
export WALLET_DIR="./Wallet_TUC50B42BDEI3XAY"
export WALLET_CONTAINER_PATH="/app/wallet"

# Optional overrides
# export JPA_DDL_AUTO=validate
# export DB_DRIVER=oracle.jdbc.OracleDriver
# export HIBERNATE_DIALECT=org.hibernate.dialect.OracleDialect
