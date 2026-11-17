package com.example.demo_app.ledger.repository;

import com.example.demo_app.ledger.entity.LedgerEntry;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
@Repository
public interface LedgerEntryRepository extends JpaRepository<LedgerEntry, Long> {
    @EntityGraph(attributePaths = {
            "moneyTransfer.fromAccount.currency",
            "moneyTransfer.toAccount.currency",
            "account.currency"})
    @Query("""
            SELECT le FROM LedgerEntry le
                        WHERE le.account.id = :accountId
                                    AND le.createdAt >= :startTime
                                                AND le.createdAt < :endTime
                                                            ORDER BY le.createdAt DESC, le.id DESC""")
    List<LedgerEntry> findLedgerEntriesInTimeInterval(@Param("accountId") Long accountId,
                                                                    @Param("startTime")Instant startTime,
                                                                    @Param("endTime")Instant endTime,
                                                                    Pageable pageable);
}
