package com.example.demo_app.transfer.entity;

import com.example.demo_app.account.entity.Account;
import com.example.demo_app.ledger.entity.LedgerEntry;
import com.example.demo_app.transfer.exception.AccountNotActiveException;
import com.example.demo_app.transfer.exception.AccountSourceNotValidException;
import com.example.demo_app.transfer.exception.UnsupportedCurrencyMatchBetweenAccountsException;
import com.example.demo_app.transfer.exception.UnsupportedSameAccountException;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Fetch;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "money_transfer")
@Getter
public class MoneyTransfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transfer_id", nullable = false, unique = true)
    private UUID transferId = UUID.randomUUID();

    @Column(name = "idempotency_key", nullable = false, unique = true)
    private UUID idempotencyKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_account_id", nullable = false)
    private Account fromAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_account_id", nullable = false)
    private Account toAccount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private MoneyTransferStatus status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "amount_to_transfer", nullable = false, precision = 19, scale = 4)
    private BigDecimal amountToTransfer;

    public void successful(){
        this.status = MoneyTransferStatus.SUCCESS;
    }

    public void setAmountToTransfer(Account fromAccount, BigDecimal amountToTransfer) {
        if (amountToTransfer == null || amountToTransfer.signum() <= 0) {
            throw new IllegalArgumentException("Amount must be positive and greater than 0");
        }
        if(!Objects.equals(fromAccount.getId(),this.fromAccount.getId())){
            throw new AccountSourceNotValidException();
        }
        this.amountToTransfer = amountToTransfer;
    }

    public void setFromAccountAndToAccount(Account from, Account to) {
        if (Objects.equals(from.getId(), to.getId())) {
            throw new UnsupportedSameAccountException();
        }
        if (!from.isActive() || !to.isActive()) {
            throw new AccountNotActiveException();
        }

        if(!Objects.equals(from.getCurrency().getCurrencyCode(), to.getCurrency().getCurrencyCode())){
            throw new UnsupportedCurrencyMatchBetweenAccountsException();
        }
        fromAccount = from;
        toAccount = to;
    }

    public void setIdempotencyKey(UUID idempotencyKey){
        if(idempotencyKey==null){
            throw new IllegalArgumentException("Idempotency key cannot be null");
        }
        this.idempotencyKey = idempotencyKey;
    }

    @PrePersist
    protected void initializeCreatedAtAndTransferStatus() {
        if (createdAt == null) {
            createdAt = Instant.now();

        }
        if(status == null){
            status = MoneyTransferStatus.SUCCESS;
        }
    }
}
