package com.example.demo_app.ledger.entity;

import com.example.demo_app.account.entity.Account;
import com.example.demo_app.transfer.entity.MoneyTransfer;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "ledger_entry")
@Getter
public class LedgerEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transfer_id", nullable = false)
    private MoneyTransfer moneyTransfer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "transfer_amount", precision = 19, scale = 4, nullable = false)
    private BigDecimal transferAmount;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @PrePersist
    protected void initializeCreatedAt() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }

    }

    protected LedgerEntry() {

    }

    public LedgerEntry(MoneyTransfer moneyTransfer, Account account) {
        this.moneyTransfer = moneyTransfer;
        this.account = account;
        if(Objects.equals(account.getId(), moneyTransfer.getFromAccount().getId())){
            transferAmount = moneyTransfer.getAmountToTransfer().negate();
        }else if (Objects.equals(account.getId(), moneyTransfer.getToAccount().getId())){
            transferAmount = moneyTransfer.getAmountToTransfer();
        }else{
            throw new IllegalArgumentException("Account is not part of this transfer");
        }
    }

}
