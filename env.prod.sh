# env.prod.sh
# Production environment for Oracle Autonomous Database via wallet.
#
# wallet service aliases are defined in:
#   ./Wallet_WSAIDBATP/tnsnames.ora

# Load .env.prod values first when present, without shell-evaluating values.
if [ -f "./.env.prod" ]; then
  while IFS= read -r line || [ -n "$line" ]; do
    case "$line" in
      ''|\#*) continue ;;
    esac

    key=${line%%=*}
    value=${line#*=}

    # Skip malformed entries without "="
    [ "$key" = "$line" ] && continue

    case "$key" in
      [A-Za-z_][A-Za-z0-9_]*) ;;
      *) continue ;;
    esac

    # Keep explicit shell/CI env vars as highest precedence.
    eval "already_set=\${$key+x}"
    if [ -n "$already_set" ]; then
      continue
    fi

    # Strip optional surrounding quotes.
    case "$value" in
      \"*\") value=${value#\"}; value=${value%\"} ;;
      \'*\') value=${value#\'}; value=${value%\'} ;;
    esac

    export "$key=$value"
  done < ./.env.prod
fi

# Use a wallet TNS alias (not a full JDBC descriptor).
export DB_URL="${DB_URL:-jdbc:oracle:thin:@wsaidbatp_high}"

# Your ADB database username and password
export DB_USER="${DB_USER:-ADMIN}"
export DB_PASSWORD="${DB_PASSWORD:-}"
export DB_DRIVER="${DB_DRIVER:-oracle.jdbc.OracleDriver}"
export HIBERNATE_DIALECT="${HIBERNATE_DIALECT:-org.hibernate.dialect.OracleDialect}"

# Spring profile
export SPRING_PROFILES_ACTIVE="${SPRING_PROFILES_ACTIVE:-prod}"

# Backend port (optional, for local run)
export SERVER_PORT="${SERVER_PORT:-8080}"

# OCI Always Free tuning
export DB_POOL_SIZE="${DB_POOL_SIZE:-4}"
export DB_POOL_MIN_IDLE="${DB_POOL_MIN_IDLE:-0}"
export DB_CONN_TIMEOUT_MS="${DB_CONN_TIMEOUT_MS:-10000}"
export DB_VALIDATION_TIMEOUT_MS="${DB_VALIDATION_TIMEOUT_MS:-5000}"
export DB_IDLE_TIMEOUT_MS="${DB_IDLE_TIMEOUT_MS:-180000}"
export DB_MAX_LIFETIME_MS="${DB_MAX_LIFETIME_MS:-600000}"
export DB_KEEPALIVE_TIME_MS="${DB_KEEPALIVE_TIME_MS:-120000}"
export TOMCAT_MAX_THREADS="${TOMCAT_MAX_THREADS:-30}"
export TOMCAT_MIN_SPARE_THREADS="${TOMCAT_MIN_SPARE_THREADS:-5}"
export TOMCAT_ACCEPT_COUNT="${TOMCAT_ACCEPT_COUNT:-100}"
export TOMCAT_CONNECTION_TIMEOUT="${TOMCAT_CONNECTION_TIMEOUT:-10s}"

# JVM tuning for low-memory compute instances
export JAVA_TOOL_OPTIONS="${JAVA_TOOL_OPTIONS:--XX:+UseG1GC -XX:+UseStringDeduplication -XX:MaxRAMPercentage=65 -XX:InitialRAMPercentage=20}"

# Comma-separated allowed frontend origins for CORS.
# For OCI deployment, replace/add your deployed frontend URL(s).
export CORS_ALLOWED_ORIGINS="${CORS_ALLOWED_ORIGINS:-http://localhost:4200,http://127.0.0.1:4200}"

# Wallet mount used by docker-compose.prod.yml
export WALLET_DIR="${WALLET_DIR:-./Wallet_WSAIDBATP}"
export WALLET_CONTAINER_PATH="${WALLET_CONTAINER_PATH:-/app/wallet}"

if [ -z "${DB_PASSWORD}" ] || [ "${DB_PASSWORD}" = "REPLACE_WITH_ADB_ADMIN_PASSWORD" ]; then
  echo "DB_PASSWORD is not set for Oracle prod. Set it in .env.prod or export DB_PASSWORD before running." >&2
  return 1 2>/dev/null || exit 1
fi

# Optional overrides
# export JPA_DDL_AUTO=validate
