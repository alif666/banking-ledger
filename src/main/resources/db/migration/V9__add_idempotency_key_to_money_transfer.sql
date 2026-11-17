-- Existing transfers predate request idempotency keys, so assign each a
-- unique placeholder before making the column mandatory.
ALTER TABLE money_transfer
    ADD COLUMN idempotency_key UUID;

UPDATE money_transfer
SET idempotency_key = gen_random_uuid()
WHERE idempotency_key IS NULL;

ALTER TABLE money_transfer
    ALTER COLUMN idempotency_key SET NOT NULL;

ALTER TABLE money_transfer
    ADD CONSTRAINT uk_money_transfer_idempotency_key UNIQUE (idempotency_key);
