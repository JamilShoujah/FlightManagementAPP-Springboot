#!/usr/bin/env sh
set -eu

ENV_MODE="dev"
ACTION="up"
DETACH=true
BUILD=true
REMOVE_VOLUMES=false

for arg in "$@"; do
  case "$arg" in
    --env=dev|--env=development)
      ENV_MODE="dev"
      ;;
    --env=prod|--env=production)
      ENV_MODE="prod"
      ;;
    --down)
      ACTION="down"
      ;;
    --no-build)
      BUILD=false
      ;;
    --foreground)
      DETACH=false
      ;;
    --volumes|-v)
      REMOVE_VOLUMES=true
      ;;
    *)
      echo "Unknown argument: $arg" >&2
      echo "Usage: npm run docker:up -- --env=dev|prod [--foreground] [--no-build]" >&2
      echo "   or: npm run docker:down -- --env=dev|prod [--volumes]" >&2
      exit 1
      ;;
  esac
done

if [ "$ENV_MODE" = "prod" ]; then
  COMPOSE_FILE="-f docker-compose.prod.yml"
  ENV_FILE="--env-file .env.prod"
else
  COMPOSE_FILE=""
  ENV_FILE="--env-file .env.local"
fi

if [ "$ACTION" = "down" ]; then
  CMD="docker compose $COMPOSE_FILE $ENV_FILE down --remove-orphans"
  if [ "$REMOVE_VOLUMES" = true ]; then
    CMD="$CMD -v"
  fi
  echo "Running: $CMD"
  sh -c "$CMD"
  exit 0
fi

CMD="docker compose $COMPOSE_FILE $ENV_FILE up"

if [ "$BUILD" = true ]; then
  CMD="$CMD --build"
fi

if [ "$DETACH" = true ]; then
  CMD="$CMD -d"
fi

echo "Running: $CMD"
sh -c "$CMD"
