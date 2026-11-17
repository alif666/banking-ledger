# Banking Ledger Demo

An educational Spring Boot application for learning relational database design, JPA, and transactional money movement. It models same-currency account transfers with an account balance and signed ledger entries.

## Technology

- Java 21 and Spring Boot
- Spring Data JPA / Hibernate
- PostgreSQL
- Flyway database migrations
- Spring Security with stateless JWT authentication

Hibernate validates the schema; Flyway owns schema changes.

## Local setup

1. Copy `.env.example` to `.env` if you do not already have a local `.env` file.
2. Set `JWT_SECRET` to a fresh Base64-encoded key containing at least 32 random bytes. Generate one with `openssl rand -base64 32`.
3. Start PostgreSQL from the project root:

   ```shell
   docker compose up -d postgres
   ```

4. Start the application:

   ```shell
   ./gradlew bootRun
   ```

   On Windows, use `gradlew.bat bootRun`.

Spring Boot imports `.env` for local configuration. Docker Compose reads the same file for the PostgreSQL username and password. `.env` is ignored by Git; commit only `.env.example`.

## API

The Postman collection at the project root contains the mapped endpoints and request variables. Main flows include registration and login, an authenticated same-currency transfer with an `Idempotency-Key`, and an account statement query with a time range and pagination.

## Money-transfer behavior

A transfer runs in one database transaction that updates both account balances, writes the transfer record, and writes signed ledger entries. The account version supports optimistic locking, and the unique idempotency key prevents duplicate successful processing of the same request key.

## Scope

This is a learning project, not a production payment processor. It does not implement external settlement, reconciliation, fraud controls, regulatory compliance, or production operational controls. Currency-specific decimal precision and rounding policy also need further work before treating amounts as production-safe.