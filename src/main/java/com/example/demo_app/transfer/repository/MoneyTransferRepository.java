package com.example.demo_app.transfer.repository;

import com.example.demo_app.transfer.entity.MoneyTransfer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MoneyTransferRepository extends JpaRepository<MoneyTransfer, Long> {

    @EntityGraph(attributePaths = {"fromAccount.currency", "toAccount.currency"})
    Optional<MoneyTransfer> findByIdempotencyKey(UUID idempotencyKey);
}
